package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.model.Role;
import com.example.BoardGameEventBackend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @GetMapping("/roles/{id}")
    public Role getRole(@PathVariable Long id){
        return roleService.getRole(id);
    }

    @PostMapping("/roles")
    public Role addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

    @PutMapping("/roles/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role){
        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);
    }

}
