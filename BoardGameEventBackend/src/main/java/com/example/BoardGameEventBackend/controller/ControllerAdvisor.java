package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.exception.RoleExistsException;
import com.example.BoardGameEventBackend.exception.RoleNotFoundException;
import com.example.BoardGameEventBackend.exception.UserExistsException;
import com.example.BoardGameEventBackend.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<?> userExistsHandler(UserExistsException exception, WebRequest request){
        Map<String, Object> nameToMessages = new HashMap<>();
        nameToMessages.put("timestamp", LocalDateTime.now());
        nameToMessages.put("msg", exception.getMessage());
        return new ResponseEntity<>(nameToMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundHandler(UserNotFoundException exception, WebRequest request){
        Map<String, Object> nameToMessages = new HashMap<>();
        nameToMessages.put("timestamp", LocalDateTime.now());
        nameToMessages.put("msg", exception.getMessage());
        return new ResponseEntity<>(nameToMessages, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleExistsException.class)
    public ResponseEntity<?> roleExistsHandler(RoleExistsException exception, WebRequest request){
        Map<String, Object> nameToMessages = new HashMap<>();
        nameToMessages.put("timestamp", LocalDateTime.now());
        nameToMessages.put("msg", exception.getMessage());
        return new ResponseEntity<>(nameToMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> roleNotFoundHandler(RoleNotFoundException exception, WebRequest request){
        Map<String, Object> nameToMessages = new HashMap<>();
        nameToMessages.put("timestamp", LocalDateTime.now());
        nameToMessages.put("msg", exception.getMessage());
        return new ResponseEntity<>(nameToMessages, HttpStatus.NOT_FOUND);
    }

}