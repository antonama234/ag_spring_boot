package ru.anton.gorbachev.ag_spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import  ru.anton.gorbachev.ag_spring_boot.models.User;
import  ru.anton.gorbachev.ag_spring_boot.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class UserController {
    private final UserServiceImpl service;

    @Autowired
    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    @GetMapping(value = "/user")
    public ModelAndView userPage(HttpServletRequest request, Principal principal) {
        User user = (User) service.loadUserByUsername(principal.getName());
        HttpSession session = request.getSession();
        session.setAttribute("greeting", "Welcome to your page " + user.getName() + " " + user.getSurname());
        return new ModelAndView("user");
    }
}