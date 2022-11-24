package com.example.BoardGameEventBackend.repository;

import com.example.BoardGameEventBackend.model.GameEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameEventRepository extends JpaRepository<GameEvent, Long> {

    boolean existsByName(String name);
}
