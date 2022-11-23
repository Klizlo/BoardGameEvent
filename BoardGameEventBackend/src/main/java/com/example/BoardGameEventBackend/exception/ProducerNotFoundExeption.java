package com.example.BoardGameEventBackend.exception;

public class ProducerNotFoundExeption extends RuntimeException {
    public ProducerNotFoundExeption(String message) {
        super("Producer" + message + "not found");
    }
}
