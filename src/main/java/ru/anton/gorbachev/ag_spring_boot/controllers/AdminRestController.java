package ru.anton.gorbachev.ag_spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.anton.gorbachev.ag_spring_boot.models.Role;
import ru.anton.gorbachev.ag_spring_boot.models.User;
import ru.anton.gorbachev.ag_spring_boot.service.UserServiceImpl;

import java.util.Collections;
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

    @GetMapping(value = "/allUsers")
    public ResponseEntity<List<User>> allUsersPage() {
        List<User> list = service.allUsers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/addUser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        System.out.println(user.getRoles());
        if (!service.isExist(user.getLogin())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(new Role(user.getRole())));
            service.addUser(user);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/editUser")
    public ResponseEntity<User> getUser(@RequestParam("id") Long id) {
        User user = service.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/editUser")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new Role("USER")));
        service.editUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteUser")
    public ResponseEntity<Long> deleteUser(@RequestBody Long id) {
        User user = service.findUserById(id);
        service.deleteUser(user);
        return ResponseEntity.ok().build();
    }
}
