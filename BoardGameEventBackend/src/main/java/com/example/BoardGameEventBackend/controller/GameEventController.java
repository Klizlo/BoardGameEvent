package com.example.BoardGameEventBackend.controller;


import com.example.BoardGameEventBackend.model.GameEvent;
import com.example.BoardGameEventBackend.service.GameEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GameEventController {
    private final GameEventService gameEventService;

    @GetMapping("/gameEvents")
    public List<GameEvent> getAllGameEvents(){
        return gameEventService.getAllGameEvents();
    }

    @GetMapping("/gameEvents/{id}")
    public GameEvent getGameEvent(@PathVariable Long id){
        return gameEventService.getGameEvent(id);
    }

    @PostMapping("/gameEvents")
    @PreAuthorize("hasAuthority('ADMIN')")
    public GameEvent saveGameEvent(@RequestBody GameEvent gameEvent){
        return gameEventService.saveGameEvent(gameEvent);
    }

    @PutMapping("/gameEvents/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public GameEvent updateGameEvent(@PathVariable Long id, @RequestBody GameEvent gameEvent){
        return gameEventService.updateGameEvent(id, gameEvent);
    }

    @DeleteMapping("/gameEvents/{id}")
    public void deleteGameEvent(@PathVariable Long id){
        gameEventService.delete(id);
    }
}