package com.example.BoardGameEventBackend.exception;

public class GameEventExistsException extends RuntimeException {
    public GameEventExistsException(String message) {
        super(message);
    }
}
