package ru.anton.gorbachev.ag_spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.anton.gorbachev.ag_spring_boot.models.User;
import ru.anton.gorbachev.ag_spring_boot.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminRestController {
    private UserServiceImpl service;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminRestController(UserServiceImpl service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> allUsersPage() {
        List<User> list = service.allUsers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/all")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (!service.isExist(user.getLogin())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            service.addUser(user);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/all/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = service.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/all")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.editUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/all/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        User user = service.findUserById(id);
        service.deleteUser(user);
        return ResponseEntity.ok().build();
    }
}
