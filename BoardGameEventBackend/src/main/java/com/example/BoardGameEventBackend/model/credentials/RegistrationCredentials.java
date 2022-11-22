package com.example.BoardGameEventBackend.model.credentials;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RegistrationCredentials {

    private String username;
    private String email;
    private String password;

}
