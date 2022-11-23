package com.example.BoardGameEventBackend.exception;

public class BoardGameExistsException extends RuntimeException {
    public BoardGameExistsException(String message) {
        super(message);
    }
}
