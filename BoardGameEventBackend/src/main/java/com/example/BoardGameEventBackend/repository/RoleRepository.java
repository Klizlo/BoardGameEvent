package com.example.BoardGameEventBackend.repository;

import com.example.BoardGameEventBackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String user);

    List<Role> findAllByNameIn(List<String> name);

    boolean existsByName(String name);
}
