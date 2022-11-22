package com.example.BoardGameEventBackend.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils implements Serializable {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expirationTime}")
    private long expirationTime;

    public String generateJwtToken(Authentication authentication){
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .claim("roles", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            e.getStackTrace();
        } catch (MalformedJwtException e) {
            e.getStackTrace();
        } catch (ExpiredJwtException e) {
            e.getStackTrace();
        } catch (UnsupportedJwtException e) {
            e.getStackTrace();
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        }

        return false;
    }

}
