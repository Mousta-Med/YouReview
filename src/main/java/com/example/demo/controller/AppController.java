package com.example.demo.controller;

import com.example.demo.model.entity.Review;
import com.example.demo.model.response.ReviewResDto;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class AppController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/home")
    public String homePage(){
        return "home";
    }
    @GetMapping("/review/add")
    public String addReviewPage(Model model){
        model.addAttribute("title", "Add Review");
        model.addAttribute("methode", "add");
        model.addAttribute("review", new ReviewResDto());
        return "manage_review";
    }

    @GetMapping("/review/edit/{id}")
    public String updateReviewPage(Model model, @PathVariable UUID id){
        ReviewResDto review = reviewService.findById(id);
        model.addAttribute("title", "Edit Review");
        model.addAttribute("methode", "update");
        model.addAttribute("review", review);
        return "manage_review";
    }


}
