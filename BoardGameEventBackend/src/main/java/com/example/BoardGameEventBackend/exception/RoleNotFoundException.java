package com.example.BoardGameEventBackend.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String id) {
        super("Role " + id + " not found");
    }
}
