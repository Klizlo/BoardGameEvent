package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.dto.EventDto;
import com.example.BoardGameEventBackend.dto.EventDtoMapper;
import com.example.BoardGameEventBackend.dto.UserDto;
import com.example.BoardGameEventBackend.dto.UserDtoMapper;
import com.example.BoardGameEventBackend.exception.ForbiddenException;
import com.example.BoardGameEventBackend.model.User;
import com.example.BoardGameEventBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public List<UserDto> getAllUsers(){
        return UserDtoMapper.mapToUserDtos(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        User loggedUser = userService.findByUsername(principal.getUsername());

        if(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.contains("ADMIN"))
                && !loggedUser.getId().equals(id)){
            throw new ForbiddenException();
        }

        return UserDtoMapper.mapToUserDto(userService.updateUser(id, user));
    }

    @GetMapping("/users/{id}/events")
    @PreAuthorize("hasAuthority('USER')")
    public List<EventDto> getUserEvents(@PathVariable Long id){
        return EventDtoMapper.mapToEventDtos(userService.getUserEvents(id));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteUser(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        User loggedUser = userService.findByUsername(principal.getUsername());

        if(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.contains("ADMIN"))
                && !loggedUser.getId().equals(id)){
            throw new ForbiddenException();
        }
        userService.delete(id);
    }

}
