package com.store.pet.exceptions;

import com.store.pet.apis.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler extends Exception {

    @ExceptionHandler(PetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Status<Object>> handlePetNotFoundException(PetNotFoundException ex) {
        final Status<Object> error = Status.builder()
                .message(ex.getMessage())
                .status(false)
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Status<Object>> handleUnknownException(Exception ex) {
        ex.printStackTrace();
        final Status<Object> error = Status.builder()
                .message(ex.getMessage())
                .status(false)
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
