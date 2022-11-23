package com.example.BoardGameEventBackend.repository;

import com.example.BoardGameEventBackend.model.BoardGameCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardGameCategoryRepository extends JpaRepository<BoardGameCategory, Long> {

    boolean existsByName(String name);
}
