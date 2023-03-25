package com.arav.blogApp.security;

import com.arav.blogApp.security.jwt.JwtAuthenticationFilter;
import com.arav.blogApp.security.jwt.JwtAuthenticationManager;
import com.arav.blogApp.security.jwt.JwtService;
import com.arav.blogApp.users.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class AppSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public AppSecurityConfig(JwtService jwtService, UserService userService) {
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter(new JwtAuthenticationManager(jwtService, userService));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.cors().disable().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET,"/about").permitAll()
                .requestMatchers(HttpMethod.POST,"/users","/users/login").permitAll()
                .requestMatchers(HttpMethod.GET,"/blogs","/blogs/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/profiles","/profiles/{username}").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(401, "Unauthorized");
                });
        return http.build();
    }
}
