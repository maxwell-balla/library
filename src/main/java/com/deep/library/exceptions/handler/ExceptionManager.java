package com.deep.library.exceptions.handler;

import com.deep.library.exceptions.*;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Throwables;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionManager extends ResponseEntityExceptionHandler {
    private static final URI BAD_REQUEST_TYPE = URI.create("https://api.deep.library.com/errors/bad-request");
    private static final String SERVICE_NAME = "library-service";

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<Object> handleTokenException(TokenException ex, WebRequest request) {
        return buildResponseEntity(ex, HttpStatus.BAD_REQUEST, request, ex.getMessage());
    }

    @ExceptionHandler(LibraryNotFoundException.class)
    public ResponseEntity<Object> handleLibraryNotFoundException(LibraryNotFoundException ex, WebRequest request) {
        return buildResponseEntity(ex, HttpStatus.NOT_FOUND, request, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return buildResponseEntity(ex, HttpStatus.NOT_FOUND, request, ex.getMessage());
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Object> handleUserExistsException(UserExistsException ex, WebRequest request) {
        return buildResponseEntity(ex, HttpStatus.CONFLICT, request, ex.getMessage());
    }

    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        return buildResponseEntity(ex, HttpStatus.NOT_FOUND, request, ex.getMessage());
    }

    @ExceptionHandler(TitleConflictException.class)
    protected ResponseEntity<Object> handleTitleConflictException(Exception ex, WebRequest request) {
        return buildResponseEntity(ex, HttpStatus.CONFLICT, request, ex.getMessage());
    }

    private ResponseEntity<Object> buildResponseEntity(Exception ex, HttpStatus status, WebRequest request, String defaultMessage) {
        log.error("Exception occurred: ", ex);

        String errorMessage = ex.getMessage();
        Throwable rootCause = Throwables.getRootCause(ex);
        if (rootCause != null && !errorMessage.equals(rootCause.getMessage())) {
            errorMessage = rootCause.getMessage();
        }

        if (errorMessage == null || errorMessage.isBlank()) {
            errorMessage = defaultMessage;
        }

        ApiResponse apiResponse = new ApiResponse(status.value(), false, errorMessage, request.getDescription(false));
        return new ResponseEntity<>(apiResponse, status);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request payload");
        problemDetail.setTitle("Bad Request");
        problemDetail.setType(BAD_REQUEST_TYPE);
        problemDetail.setProperty("error", errors);
        problemDetail.setProperty("service", SERVICE_NAME);
        problemDetail.setProperty("error_category", "Generic");
        problemDetail.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
