package com.arav.blogApp.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET = "ascjb27893rh23njdkacs67t23ndkmalcso2398ufsnjck67278398enjkas";
    Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public String createJwt(String username) {
        if(username == null || username.isEmpty())
            throw new IllegalArgumentException("Username cannot be null or empty");
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public String getUsernameFromJwt(String jwtToken) {
        return JWT.require(algorithm)
                .build()
                .verify(jwtToken)
                .getSubject();
    }
}
