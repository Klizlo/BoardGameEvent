package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.dto.UserDto;
import com.example.BoardGameEventBackend.dto.UserDtoMapper;
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
    public List<UserDto> getAllUsers(){
        return UserDtoMapper.mapToUserDtos(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public UserDto getUser(@PathVariable Long id){
        return UserDtoMapper.mapToUserDto(userService.getUser(id));
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDto saveUser(@RequestBody User user){
        return UserDtoMapper.mapToUserDto(userService.saveUser(user));
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public UserDto updateUser(@PathVariable Long id, @Valid @RequestBody User user){
        return UserDtoMapper.mapToUserDto(userService.updateUser(id, user));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteUser(@PathVariable Long id){
        userService.delete(id);
    }

}
