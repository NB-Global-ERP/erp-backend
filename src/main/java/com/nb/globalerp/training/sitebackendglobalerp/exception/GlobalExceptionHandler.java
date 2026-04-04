package com.nb.globalerp.training.sitebackendglobalerp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleStudentAlreadyExists(StudentAlreadyExistsException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),  // 409
                "Student Already Exists",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}
