package ru.anton.gorbachev.ag_spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import  ru.anton.gorbachev.ag_spring_boot.models.User;
import  ru.anton.gorbachev.ag_spring_boot.service.UserServiceImpl;

@Controller
public class DeleteUserController {
    private UserServiceImpl service;

    @Autowired
    public DeleteUserController(UserServiceImpl service) {
        this.service = service;
    }

    @PostMapping(value = "/admin/deleteUser")
    public String deleteUserPage(@RequestParam("id") Long id) {
        User user = service.findUserById(id);
        service.deleteUser(user);
        return "redirect:/admin/allUsers";
    }
}
