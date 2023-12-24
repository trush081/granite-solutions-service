package com.trentonrush.granitesolutions.auth;

import com.trentonrush.granitesolutions.service.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthenticationToken implements Authentication {

    private final UserDetails userDetails;
    private final String token;

    private final JwtService jwtService;
    private boolean isAuthenticated;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtAuthenticationToken(UserDetails userDetails, String token, Collection<? extends GrantedAuthority> authorities, JwtService jwtService) {
        this.userDetails = userDetails;
        this.token = token;
        this.authorities = authorities;
        this.jwtService = jwtService;
        setAuthenticated(jwtService.isTokenValid(token, userDetails));
    }

    // Getters, Setters, and other necessary methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null; // Not needed for JWT as credentials are usually the token itself
    }

    @Override
    public Object getDetails() {
        return null; // Additional details if needed
    }

    @Override
    public Object getPrincipal() {
        return userDetails; // Principal if needed
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userDetails.getUsername(); // Name or identifier if needed
    }
}
