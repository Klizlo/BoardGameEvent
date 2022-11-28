package com.example.BoardGameEventBackend.controller;


import com.example.BoardGameEventBackend.dto.*;
import com.example.BoardGameEventBackend.exception.EventNotFoundException;
import com.example.BoardGameEventBackend.exception.ForbiddenException;
import com.example.BoardGameEventBackend.model.Event;
import com.example.BoardGameEventBackend.model.User;
import com.example.BoardGameEventBackend.service.EventService;
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
public class EventController {
    private final EventService eventService;
    private final UserService userService;

    @GetMapping("/events")
    public List<EventDto> getAllGameEvents(){
        return EventDtoMapper.mapToEventDtos(eventService.getAllGameEvents());
    }

    @GetMapping("/events/{id}")
    public EventWithPlayersDto getGameEvent(@PathVariable Long id){
        return EventWithPlayersDtoMapper.mapToEventWithPlayersDto(eventService.getGameEvent(id));
    }

    @PostMapping("/events")
    @PreAuthorize("hasAuthority('ADMIN')")
    public EventDto saveGameEvent(@Valid @RequestBody Event event){
        return EventDtoMapper.mapToEventDto(eventService.saveGameEvent(event));
    }

    @PutMapping("/events/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public EventDto updateGameEvent(@PathVariable Long id, @Valid @RequestBody Event event){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        User loggedUser = userService.findByUsername(principal.getUsername());

        if(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.contains("ADMIN"))
                && !loggedUser.getId().equals(id)){
            throw new ForbiddenException();
        }

        return EventDtoMapper.mapToEventDto(eventService.updateGameEvent(id, event));
    }

    @GetMapping("/events/{eventId}/players")
    @PreAuthorize("hasAuthority('USER')")
    public List<UserDto> getEventPlayers(@PathVariable("eventId") Long eventId){
        return UserDtoMapper.mapToUserDtos(eventService.getEventPlayers(eventId));
    }

    @PostMapping("/events/{eventId}/players/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public EventDto addPlayerToEvent(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId){
        return EventDtoMapper.mapToEventDto(eventService.addPlayerToEvent(eventId, userId));
    }

    @DeleteMapping("/events/{eventId}/players/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public EventDto removePlayerToEvent(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        User loggedUser = userService.findByUsername(principal.getUsername());

        Event event = eventService.getGameEvent(eventId);

        if(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.contains("ADMIN"))
                && !loggedUser.getId().equals(userId) && !loggedUser.getId().equals(event.getOrganizer().getId())){
            throw new ForbiddenException();
        }

        return EventDtoMapper.mapToEventDto(eventService.removePlayerToEvent(eventId, userId));
    }

    @DeleteMapping("/events/{id}")
    public void deleteGameEvent(@PathVariable Long id){
        eventService.delete(id);
    }
}