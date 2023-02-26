package com.example.loanapp.controller;

import com.example.loanapp.model.ERole;
import com.example.loanapp.model.User;
import com.example.loanapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User addUser(@RequestParam("username") String username,
                        @RequestParam("email") String email,
                        @RequestParam("password") String password){
        HashSet<ERole> roles = new HashSet<>();
        roles.add(ERole.ROLE_USER);
        return userService.addUser(new User(username,email,password,roles));
    }

}
