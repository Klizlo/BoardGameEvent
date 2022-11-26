package com.example.BoardGameEventBackend.exception;


public class BoardGameNotFoundException extends RuntimeException {

    public BoardGameNotFoundException(String message) {super("Board Game " + message + " not found");}
}
