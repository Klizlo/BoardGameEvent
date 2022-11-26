package com.example.BoardGameEventBackend.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message) {
        super("Event " + message + " not found");
    }
}
