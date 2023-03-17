package com.arav.blogApp.security.jwt;

import com.arav.blogApp.users.dtos.UserResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication implements Authentication {
    private String jwtToken;
    private UserResponseDto user;

    public JwtAuthentication(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getCredentials() {
        return jwtToken;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public UserResponseDto getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return user!=null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
