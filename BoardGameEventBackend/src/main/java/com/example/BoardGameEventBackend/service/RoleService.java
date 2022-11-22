package com.example.BoardGameEventBackend.service;

import com.example.BoardGameEventBackend.exception.RoleExistsException;
import com.example.BoardGameEventBackend.exception.RoleNotFoundException;
import com.example.BoardGameEventBackend.model.Role;
import com.example.BoardGameEventBackend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRole(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id.toString()));
    }

    public Role addRole(Role role) {

        if(roleRepository.existsByName(role.getName())){
            throw new RoleExistsException(role.getName());
        }

        return roleRepository.save(role);
    }

    public Role updateRole(Long id, Role role) {
        Role roleToUpdate = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id.toString()));

        if(roleRepository.existsByName(role.getName())){
            throw new RoleExistsException(role.getName());
        }

        roleToUpdate.setName(role.getName());

        return roleRepository.save(roleToUpdate);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
