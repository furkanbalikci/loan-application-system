package com.example.loanapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api/v1")
public class HomeController {
    //For make endpoints looks like from /login.html to /login
    @GetMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }

    //For handling to the "templates/favicon.ico.html" could not be resolved error.
    @GetMapping("favicon.ico")
    @ResponseBody
    public void favicon(){}
}
