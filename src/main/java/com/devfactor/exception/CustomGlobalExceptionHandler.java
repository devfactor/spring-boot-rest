package com.devfactor.exception;

import com.devfactor.model.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // add more handlers here for custom exceptions
    @ExceptionHandler(NumberNotFoundException.class)
    public final ResponseEntity<ErrorData> handleNumberNotFoundException(NumberNotFoundException ex, WebRequest request) {
        ErrorData errorData = new ErrorData(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }
}
