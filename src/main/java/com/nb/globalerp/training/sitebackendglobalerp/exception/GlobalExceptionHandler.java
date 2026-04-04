package com.nb.globalerp.training.sitebackendglobalerp.exception;

import jakarta.persistence.EntityNotFoundException;
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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Entity Not Found", ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex);
    }

    private ResponseEntity<ApiError> buildErrorResponse(HttpStatus status, String error, Exception ex) {
        log.error("Handling exception: error={}, message={}", ExceptionUtils.getRootCause(ex), ExceptionUtils.getRootCauseMessage(ex));
        ApiError apiError = new ApiError(status.value(), error, ExceptionUtils.getRootCauseMessage(ex));
        return ResponseEntity.status(status).body(apiError);
    }
}
