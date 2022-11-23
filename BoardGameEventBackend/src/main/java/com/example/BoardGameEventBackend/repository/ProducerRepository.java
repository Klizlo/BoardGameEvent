package com.example.BoardGameEventBackend.repository;

import com.example.BoardGameEventBackend.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

    boolean existsByName(String name);
}
