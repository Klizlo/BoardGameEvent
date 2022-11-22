package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.model.User;
import com.example.BoardGameEventBackend.model.credentials.LoginCredentials;
import com.example.BoardGameEventBackend.model.credentials.RegistrationCredentials;
import com.example.BoardGameEventBackend.security.JwtUtils;
import com.example.BoardGameEventBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials loginCredentials){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginCredentials.getUsername(), loginCredentials.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok().body(new HashMap<>(Map.of("token", token)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationCredentials registrationCredentials){
        User user = new User();
        user.setUsername(registrationCredentials.getUsername());
        user.setEmail(registrationCredentials.getEmail());
        user.setPassword(registrationCredentials.getPassword());

        userService.saveUser(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registrationCredentials.getUsername(), registrationCredentials.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok().body(new HashMap<>(Map.of("token", token)));
    }

}
