package ru.anton.gorbachev.ag_spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.anton.gorbachev.ag_spring_boot.models.Role;
import  ru.anton.gorbachev.ag_spring_boot.models.User;
import  ru.anton.gorbachev.ag_spring_boot.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    private UserServiceImpl service;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserServiceImpl service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/allUsers")
    public ModelAndView allUsersPage(HttpServletRequest request, Principal principal) {
        User user = (User) service.loadUserByUsername(principal.getName());
        HttpSession session = request.getSession();
        session.setAttribute("greeting", "Welcome to your page " + user.getName() + " " + user.getSurname());
        List<User> list = service.allUsers();
        return new ModelAndView("all", "allUsers", list);
    }

    @PostMapping(value = "/addUser",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<User> addUser(@RequestBody User user, @RequestParam String role) {
        if (!service.isExist(user.getLogin())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(new Role(role)));
            service.addUser(user);
        }
        return ResponseEntity.ok().build();
    }

//    @PostMapping(value = "/addUser")
//    public String addUser(@ModelAttribute("user") User user) {
//        if (!service.isExist(user.getLogin())) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            user.setRoles(Collections.singleton(new Role("USER")));
//            service.addUser(user);
//        }
//        return "redirect:/admin/allUsers";
//    }

    @PutMapping(value = "/editUser")
    public String editUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new Role("USER")));
        service.editUser(user);
        return "redirect:/admin/allUsers";
    }

    @DeleteMapping(value = "/deleteUser")
    public String deleteUserPage(@RequestParam("id") Long id) {
        User user = service.findUserById(id);
        service.deleteUser(user);
        return "redirect:/admin/allUsers";
    }
}
