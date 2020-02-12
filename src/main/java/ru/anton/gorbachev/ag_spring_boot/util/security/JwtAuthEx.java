package ru.anton.gorbachev.ag_spring_boot.util.security;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthEx extends AuthenticationException {

    public JwtAuthEx(String msg) {
        super(msg);
    }
}
