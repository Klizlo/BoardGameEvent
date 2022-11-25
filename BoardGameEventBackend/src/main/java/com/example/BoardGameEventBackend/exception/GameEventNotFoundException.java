package com.example.BoardGameEventBackend.exception;

public class GameEventNotFoundException extends RuntimeException {
    public GameEventNotFoundException(String message) {
        super(message);
    }
}
