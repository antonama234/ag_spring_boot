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
public class EditUserController {
    private UserServiceImpl service;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EditUserController(UserServiceImpl service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/admin/editUser")
    public String editUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new Role("USER")));
        service.editUser(user);
        return "redirect:/admin/allUsers";
    }
}
