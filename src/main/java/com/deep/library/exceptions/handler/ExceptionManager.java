package com.deep.library.exceptions.handler;

import com.deep.library.exceptions.TitleConflictException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Throwables;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;

@RestControllerAdvice
@Slf4j
public class ExceptionManager extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TitleConflictException.class)
    protected ResponseEntity<Object> handleTitleConflictException(Exception ex, WebRequest request) {
        return buildResponseEntity(ex, HttpStatus.CONFLICT, request, "Conflict occurred");
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
