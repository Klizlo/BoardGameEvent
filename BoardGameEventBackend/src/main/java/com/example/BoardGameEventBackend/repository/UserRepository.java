package com.example.BoardGameEventBackend.repository;

import com.example.BoardGameEventBackend.model.Event;
import com.example.BoardGameEventBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String username);

    @Query("select u.events from User u where u.id = ?1")
    List<Event> findEventsByUser(Long id);

    @Query("select u.createdEvents from User u where u.id = ?1")
    List<Event> findCreatedEventsByUser(Long id);

}
