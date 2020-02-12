package ru.anton.gorbachev.ag_spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.anton.gorbachev.ag_spring_boot.models.User;
import ru.anton.gorbachev.ag_spring_boot.service.UserServiceImpl;

@RestController
public class RegistrationController {
    private final UserServiceImpl service;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserServiceImpl service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/reg")
    public ResponseEntity<User> regUser(@RequestBody User user) {
        if (!service.isExist(user.getLogin())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            service.addUser(user);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
