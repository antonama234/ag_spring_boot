package ru.anton.gorbachev.ag_spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.anton.gorbachev.ag_spring_boot.models.Role;
import  ru.anton.gorbachev.ag_spring_boot.models.User;
import  ru.anton.gorbachev.ag_spring_boot.service.UserServiceImpl;

import java.util.Collections;

@Controller
public class AddUserController {
    private UserServiceImpl service;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AddUserController(UserServiceImpl service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/admin/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        if (!service.isExist(user.getLogin())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(new Role("USER")));
            service.addUser(user);
        }
        return "redirect:/admin/allUsers";
    }
}