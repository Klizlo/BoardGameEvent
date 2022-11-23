package com.example.BoardGameEventBackend.exception;

public class BoardGameCategoryNotFoundExeption extends RuntimeException{

    public BoardGameCategoryNotFoundExeption(String message) {super("Board Game Category " + message + " not found");}
}
