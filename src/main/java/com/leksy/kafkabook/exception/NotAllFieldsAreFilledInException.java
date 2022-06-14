package com.leksy.kafkabook.exception;

public class NotAllFieldsAreFilledInException extends RuntimeException{
    public NotAllFieldsAreFilledInException(String message) {
        super(message);
    }
}
