package com.example.BoardGameEventBackend.repository;

import com.example.BoardGameEventBackend.model.Event;
import com.example.BoardGameEventBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    boolean existsByName(String name);

    @Query("select e.players from Event e where e.id = ?1")
    List<User> findPlayersByEvent(Long id);
}
