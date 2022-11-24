package com.example.BoardGameEventBackend.service;

import com.example.BoardGameEventBackend.exception.GameEventExistsException;
import com.example.BoardGameEventBackend.exception.GameEventNotFoundException;
import com.example.BoardGameEventBackend.model.GameEvent;
import com.example.BoardGameEventBackend.repository.GameEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameEventService {

    private final GameEventRepository gameEventRepository;

    public List<GameEvent> getAllGameEvents(){
        return gameEventRepository.findAll();
    }

    public GameEvent getGameEvent(Long id){
        return gameEventRepository.findById(id).orElseThrow(() -> new GameEventNotFoundException(id.toString()));
    }

    public GameEvent saveGameEvent(GameEvent gameEvent){
        if(gameEventRepository.existsByName(gameEvent.getName())){
            throw new GameEventExistsException("Name " + gameEvent.getName() + " is already taken.");
        }

        return gameEventRepository.save(gameEvent);
    }

    public GameEvent updateGameEvent(Long id, GameEvent gameEvent) {
        GameEvent gameEventToEdit = gameEventRepository.findById(id).orElseThrow(() -> new GameEventNotFoundException(id.toString()));

        if(gameEventRepository.existsByName(gameEvent.getName())){
            throw new GameEventExistsException("Name " + gameEvent.getName() + " is already used.");
        }

        gameEventToEdit.setName(gameEvent.getName());
        gameEventToEdit.setNumberOfPlayers(gameEvent.getNumberOfPlayers());
        gameEventToEdit.setDescription(gameEvent.getDescription());

        return gameEventRepository.save(gameEventToEdit);
    }

    public void delete(Long id){
        gameEventRepository.deleteById(id);
    }
}
