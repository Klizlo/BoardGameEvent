package com.example.BoardGameEventBackend.repository;

import com.example.BoardGameEventBackend.model.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {

    Optional<BoardGame> findByName(String name);

    boolean existsByName(String name);
}
