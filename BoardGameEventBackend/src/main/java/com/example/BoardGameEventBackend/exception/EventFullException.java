package com.example.BoardGameEventBackend.exception;

public class EventFullException extends RuntimeException {

    public EventFullException() {
        super("Event is full");
    }
}
