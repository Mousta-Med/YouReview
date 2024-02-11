package com.example.demo.controller;

import com.example.demo.model.request.ReviewReqDto;
import com.example.demo.model.response.ReviewResDto;
import com.example.demo.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("")
    public String getAllReviews(Model model) {
        List<ReviewResDto> reviews = reviewService.findAll();
        model.addAttribute("reviews", reviews);
        return "review";
    }

    @GetMapping("/{id}")
    public String getReviewById(Model model, @PathVariable UUID id) {
        model.addAttribute("review", reviewService.findById(id));
        return "review";
    }


    @PostMapping("/add")
    public String addReview(@Valid @ModelAttribute("review") ReviewReqDto review, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "message or title cannot be empty");
            model.addAttribute("methode", "save");
            return "manage_review";
        }
        reviewService.save(review);
        model.addAttribute("review", review);
        getAllReviews(model);
        return "review";
    }


    @PostMapping("/update")
    public String updateReview(HttpServletResponse response,@Valid @ModelAttribute("review") ReviewReqDto review, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("error", "message or title cannot be empty");
            model.addAttribute("methode", "update");
            return "manage_review";
        }
        reviewService.update(review.getId(), review);
        model.addAttribute("review", review);
        getAllReviews(model);
        response.sendRedirect("/review");
        return "review";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(HttpServletResponse response, Model model, @PathVariable UUID id) throws IOException {
        reviewService.delete(id);
        getAllReviews(model);
        response.sendRedirect("/review");
        return "review";
    }


}
