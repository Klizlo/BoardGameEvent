package com.example.BoardGameEventBackend.exception;

public class ProducerExistsException extends RuntimeException {
    public ProducerExistsException(String message) {
        super(message);
    }
}
