package com.leksy.kafkabook.controller;

import com.leksy.kafkabook.exception.NotAllFieldsAreFilledInException;
import com.leksy.kafkabook.exception.NotValidHttpMethodException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BookAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = NotAllFieldsAreFilledInException.class)
    protected ResponseEntity<Object> notAllFieldsAreFilledIn(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "please filling all field in JSON(producer)";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NO_CONTENT, request);
    }

    @ExceptionHandler(value = NotValidHttpMethodException.class)
    protected ResponseEntity<Object> notValidHttpMethod(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "please enter valid httpMethod";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NO_CONTENT, request);
    }
}
