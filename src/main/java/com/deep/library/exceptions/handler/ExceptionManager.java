package com.deep.library.exceptions.handler;

import com.deep.library.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Throwables;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ExceptionManager extends ResponseEntityExceptionHandler {

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
}
