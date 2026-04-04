package com.nb.globalerp.training.sitebackendglobalerp.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleStudentAlreadyExists(StudentAlreadyExistsException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Student Already Exists", ex);
    }

    private ResponseEntity<ApiError> buildErrorResponse(HttpStatus status, String error, Exception ex) {
        log.error("Handling exception: error={}, message={}", ExceptionUtils.getRootCause(ex), ExceptionUtils.getRootCauseMessage(ex));
        ApiError apiError = new ApiError(status.value(), error, ex.getMessage());
        return ResponseEntity.status(status).body(apiError);
    }
}
