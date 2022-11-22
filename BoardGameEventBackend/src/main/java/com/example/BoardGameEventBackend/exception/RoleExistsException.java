package com.example.BoardGameEventBackend.exception;

public class RoleExistsException extends RuntimeException {
    public RoleExistsException(String name) {
        super("Role " + name + " already exists");
    }
}
