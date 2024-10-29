package com.deep.library.security;

import com.deep.library.domains.dto.RefreshRequest;
import com.deep.library.domains.dto.RefreshResponse;
import com.deep.library.domains.entities.UserEntity;
import com.deep.library.exceptions.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    public static final long ACCESS_TOKEN_VALIDITY = 60 * 60; // 1H
    public static final long REFRESH_TOKEN_VALIDITY = 24 * 60 * 60; // 24H

    public String extractUsername(String token) {
        log.info("extract the username from the accessToken");
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, ACCESS_TOKEN_VALIDITY, "ACCESS");
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, REFRESH_TOKEN_VALIDITY, "REFRESH");
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration,
            String tokenType
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .claim("tokenType", tokenType)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (!claims.get("tokenType").equals("ACCESS")) {
                throw new TokenException("It's not ACCESS token");
            }
            log.info("checking if the accessToken is valid");
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new TokenException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new TokenException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new TokenException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new TokenException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new TokenException("JWT claims string is empty");
        }
    }

    private boolean isTokenExpired(String token) {
        log.info("checking if the accessToken is expired");
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String refreshAccessToken(String refreshToken) {
        try {
            final Claims claims = extractAllClaims(refreshToken);
            if (!claims.get("tokenType").equals("REFRESH")) {
                log.error("refresh token expired");
                throw new TokenException("Invalid token type");
            }
            if (isTokenExpired(refreshToken)) {
                log.error("refresh token expired");
                throw new TokenException("Refresh token type");
            }

            String username = extractUsername(refreshToken);
            return generateAccessToken(UserEntity.builder()
                    .username(username)
                    .build());
        } catch (Exception e) {
            log.error("refresh access token error", e);
            throw new TokenException("Invalid refresh token");
        }
    }

    public RefreshResponse handleRefreshToken(RefreshRequest request) {
        try {
            String refreshToken = request.refreshToken();
            if (refreshToken == null || refreshToken.startsWith("Bearer ")) {
                throw new TokenException("No refresh token provided");
            }

            return RefreshResponse.builder()
                    .accessToken(refreshAccessToken(refreshToken))
                    .build();
        } catch (Exception e) {
            throw new TokenException("Refresh access token error");
        }
    }
}
