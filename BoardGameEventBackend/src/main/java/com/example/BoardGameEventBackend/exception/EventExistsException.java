package com.example.BoardGameEventBackend.exception;

public class EventExistsException extends RuntimeException {
    public EventExistsException(String message) {
        super(message);
    }
}
