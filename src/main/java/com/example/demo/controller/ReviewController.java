package com.example.demo.controller;

import com.example.demo.model.request.ReviewReqDto;
import com.example.demo.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/")
    public String getAllReviews(Model model) {
        model.addAttribute("reviews", reviewService.findAll());
        return "review";
    }

    @GetMapping("/{id}")
    public String getReviewById(Model model, @PathVariable UUID id) {
        model.addAttribute("review", reviewService.findById(id));
        return "review";
    }

    @PostMapping("/")
    public String addReview(@Valid @RequestBody ReviewReqDto review){
        reviewService.save(review);
        return "review";
    }


    @PutMapping("/{id}")
    public String updateReview(@Valid @PathVariable UUID id, @RequestBody ReviewReqDto review){
        reviewService.update(id, review);
        return "review";
    }

    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable UUID id){
        reviewService.delete(id);
        return "review";
    }


}
