package com.example.BoardGameEventBackend.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private Set<RoleDto> roles;

}
