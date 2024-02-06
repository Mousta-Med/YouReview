package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/home")
    public String homePage(){
        return "home";
    }
    @GetMapping("/review")
    public String reviewPage(){
        return "review";
    }
    @GetMapping("/reviews")
    public String reviewsPage(){
        return "reviews";
    }


}
