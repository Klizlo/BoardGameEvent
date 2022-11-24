package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.model.Role;
import com.example.BoardGameEventBackend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @GetMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role getRole(@PathVariable Long id){
        return roleService.getRole(id);
    }

    @PostMapping("/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role addRole(@Valid @RequestBody Role role){
        return roleService.addRole(role);
    }

    @PutMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role updateRole(@PathVariable Long id, @Valid  @RequestBody Role role){
        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);
    }

}
