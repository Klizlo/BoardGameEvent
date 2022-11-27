package com.example.BoardGameEventBackend.service;

import com.example.BoardGameEventBackend.exception.*;
import com.example.BoardGameEventBackend.model.Event;
import com.example.BoardGameEventBackend.model.User;
import com.example.BoardGameEventBackend.repository.EventRepository;
import com.example.BoardGameEventBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public List<Event> getAllGameEvents(){
        return eventRepository.findAll();
    }

    public Event getGameEvent(Long id){
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id.toString()));
    }

    @Transactional
    public Event saveGameEvent(Event event){
        if(eventRepository.existsByName(event.getName())){
            throw new EventExistsException("Name " + event.getName() + " is already taken.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        User loggedUser = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(principal.getUsername()));

        event.setOrganizer(loggedUser);

        return eventRepository.save(event);
    }

    @Transactional
    public Event updateGameEvent(Long id, Event event) {

        Event eventToEdit = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id.toString()));

        if(eventRepository.existsByName(event.getName())){
            throw new EventExistsException("Name " + event.getName() + " is already used.");
        }

        eventToEdit.setName(event.getName());
        eventToEdit.setNumberOfPlayers(event.getNumberOfPlayers());
        eventToEdit.setDescription(event.getDescription());
        eventToEdit.setDate(event.getDate());

        return eventRepository.save(eventToEdit);
    }

    public List<User> getEventPlayers(Long eventId) {
        return eventRepository.findPlayersByEvent(eventId);
    }

    @Transactional
    public Event addPlayerToEvent(Long gameEventId, Long userId){
        Event event = eventRepository.findById(gameEventId)
                .orElseThrow(() -> new EventNotFoundException(gameEventId.toString()));

        if(event.getPlayers().size() == event.getNumberOfPlayers()){
            throw new EventFullException();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        user.addEvent(event);
        event.addPlayer(user);

        return eventRepository.save(event);
    }

    @Transactional
    public Event removePlayerToEvent(Long eventId, Long userId){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId.toString()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        user.removeEvent(event);
        event.removePlayer(user);

        return eventRepository.save(event);
    }

    public void delete(Long id){
        eventRepository.deleteById(id);
    }
}
