package com.arav.blogApp.security.jwt;

import com.arav.blogApp.users.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationManager implements AuthenticationManager {
    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthenticationManager(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof JwtAuthentication) {
            JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;

            var jwtToken = jwtAuthentication.getCredentials();
            var username = jwtService.getUsernameFromJwt(jwtToken);
            var user = userService.findUserbyUsername(username);
            jwtAuthentication.setUser(user);
            return jwtAuthentication;
        }
        return null;
    }
}
