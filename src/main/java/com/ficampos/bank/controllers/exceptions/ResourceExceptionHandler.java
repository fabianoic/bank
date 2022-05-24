package com.ficampos.bank.controllers.exceptions;

import com.ficampos.bank.services.exceptions.EntityAlreadyExistsException;
import com.ficampos.bank.services.exceptions.EntityNotFoundException;
import com.ficampos.bank.services.exceptions.InputInvalidException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        ErrorResponse errorResponse = mountResponse(e.getMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> entityAlreadyExists(EntityAlreadyExistsException e, HttpServletRequest request) {
        ErrorResponse errorResponse = mountResponse(e.getMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(InputInvalidException.class)
    public ResponseEntity<ErrorResponse> inputInvalidException(InputInvalidException e, HttpServletRequest request) {
        ErrorResponse errorResponse = mountResponse(e.getMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.setMessage("Um ou mais campos estão inválidos!");
        response.setStatus(status.value());
        response.setTimestamp(Instant.now());

        List<FieldInputError> errors = ex.getBindingResult().getAllErrors().stream().map(error -> {
            String name = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            return new FieldInputError(name, message);
        }).collect(Collectors.toList());

        response.setFields(errors);
        return handleExceptionInternal(ex, response, headers, status, request);
    }

    private ErrorResponse mountResponse(String message, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(Instant.now());
        errorResponse.setMessage(message);
        errorResponse.setStatus(status.value());
        return errorResponse;
    }
}
