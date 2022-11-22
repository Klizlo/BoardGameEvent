package com.example.BoardGameEventBackend.model.credentials;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginCredentials {

    private String username;
    private String password;

}
