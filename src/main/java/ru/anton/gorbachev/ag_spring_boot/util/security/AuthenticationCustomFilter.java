package ru.anton.gorbachev.ag_spring_boot.util.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.anton.gorbachev.ag_spring_boot.models.UserDTO;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationCustomFilter extends UsernamePasswordAuthenticationFilter {
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private AuthProvider provider;

    public AuthenticationCustomFilter(InMemoryUserDetailsManager inMemoryUserDetailsManager, AuthProvider provider) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.provider = provider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try  {
            UserDTO userDTO = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);
            return new UsernamePasswordAuthenticationToken(
                    userDTO.getUsername(),
                    userDTO.getPassword(),
                    new ArrayList<>());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(authentication.getName());
        String token = provider.createToken(userDetails);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(3_600);
        response.addCookie(cookie);
    }
}
