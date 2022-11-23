package com.example.BoardGameEventBackend.exception;


public class BoardGameNotFoundExeption extends RuntimeException {

    public BoardGameNotFoundExeption(String message) {super("Board Game " + message + " not found");}
}
