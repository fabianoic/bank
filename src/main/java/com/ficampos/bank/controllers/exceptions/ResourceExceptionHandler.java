package com.ficampos.bank.controllers.exceptions;

import com.ficampos.bank.services.exceptions.EntityAlreadyExistsException;
import com.ficampos.bank.services.exceptions.EntityNotFoundException;
import com.ficampos.bank.services.exceptions.InputInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        ResponseError responseError = mountResponse(e.getMessage(), HttpStatus.NOT_FOUND);

        return ResponseEntity.status(responseError.getStatus()).body(responseError);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ResponseError> entityAlreadyExists(EntityAlreadyExistsException e, HttpServletRequest request) {
        ResponseError responseError = mountResponse(e.getMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(responseError.getStatus()).body(responseError);
    }

    @ExceptionHandler(InputInvalidException.class)
    public ResponseEntity<ResponseError> inputInvalidException(InputInvalidException e, HttpServletRequest request) {
        ResponseError responseError = mountResponse(e.getMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(responseError.getStatus()).body(responseError);
    }

    private ResponseError mountResponse(String message, HttpStatus status) {
        ResponseError responseError = ResponseError.builder()
                .timestamp(Instant.now())
                .message(message)
                .status(status.value())
                .build();
        return responseError;
    }
}
