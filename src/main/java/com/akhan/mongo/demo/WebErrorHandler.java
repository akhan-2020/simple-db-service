package com.akhan.mongo.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class WebErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HttpErrorResponse> handleCustomException(DataAccessException ce) {

        HttpErrorResponse response = new HttpErrorResponse();
        response.setErrorCategory("Data");
        response.setErrorMessage(ce.getMessage());

        return new ResponseEntity<HttpErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }
}
