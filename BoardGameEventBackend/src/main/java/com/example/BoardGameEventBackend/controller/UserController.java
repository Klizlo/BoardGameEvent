package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.model.Role;
import com.example.BoardGameEventBackend.model.User;
import com.example.BoardGameEventBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @PutMapping("/users/{id}/roles")
    public User addRolesToUser(@PathVariable Long id, @RequestBody List<Role> roles){
        return userService.addRolesToUser(id, roles);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.delete(id);
    }

}
