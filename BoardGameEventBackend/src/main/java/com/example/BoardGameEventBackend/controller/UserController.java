package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.model.Role;
import com.example.BoardGameEventBackend.model.User;
import com.example.BoardGameEventBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @PutMapping("/users/{id}/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User addRolesToUser(@PathVariable Long id, @Valid @RequestBody List<Role> roles){
        return userService.addRolesToUser(id, roles);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteUser(@PathVariable Long id){
        userService.delete(id);
    }

}
