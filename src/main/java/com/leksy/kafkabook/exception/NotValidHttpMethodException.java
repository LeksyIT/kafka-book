package com.leksy.kafkabook.exception;

public class NotValidHttpMethodException extends RuntimeException {
    public NotValidHttpMethodException(String message) {
        super(message);
    }
}
