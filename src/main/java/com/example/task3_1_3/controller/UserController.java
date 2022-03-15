package com.example.task3_1_3.controller;

import com.example.task3_1_3.model.Role;
import com.example.task3_1_3.model.User;
import com.example.task3_1_3.service.RoleService;
import com.example.task3_1_3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "list";
    }

    @GetMapping("/user/{name}")
    public String getUser(Model model, @PathVariable String name) {
        User user = userService.findByUsername(name);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/update/")
    public String updateUser(@RequestParam long id, @RequestParam String name, @RequestParam String surname,
                             @RequestParam String username, @RequestParam String password, @RequestParam long[] roles,
                             @RequestParam int age) {
        userService.updateUser(name, surname, username, password, roles, age, id);
        return "redirect:/";
    }



    @PostMapping("/delete/")
    public String deleteUser(@RequestParam long id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/save/")
    public String saveUser(@RequestParam String name, @RequestParam String surname,
                           @RequestParam String username, @RequestParam String password, @RequestParam long[] roles,
                           @RequestParam int age) {
        userService.createUser(name, surname, username, password, roles, age);
        return "redirect:/";
    }

}