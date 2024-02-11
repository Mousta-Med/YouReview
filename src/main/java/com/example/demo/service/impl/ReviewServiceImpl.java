package com.example.demo.service.impl;

import com.example.demo.model.request.ReviewReqDto;
import com.example.demo.model.response.ReviewResDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.entity.Review;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    PersonRepository personRepository;

    @Override
    public ReviewResDto save(ReviewReqDto reviewReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Review review = modelMapper.map(reviewReqDto, Review.class);
        review.setPerson(personRepository.findByUsername(authentication.getName()).get());
        return modelMapper.map(reviewRepository.save(review), ReviewResDto.class);
    }

    @Override
    public List<ReviewResDto> findAll() {
        return reviewRepository.findAll().stream().map(review -> modelMapper.map(review, ReviewResDto.class)).collect(Collectors.toList());
    }

    @Override
    public ReviewResDto findById(UUID id) {
        return reviewRepository.findById(id)
                .map(review -> modelMapper.map(review, ReviewResDto.class)).orElseThrow(() -> new ResourceNotFoundException("Review Not found with this: " + id));
    }

    @Override
    public ReviewResDto update(UUID id, ReviewReqDto reviewReqDto) {
        Review existingReview = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review Not found with this: " + id));
        BeanUtils.copyProperties(reviewReqDto, existingReview);
        existingReview.setId(id);
        return modelMapper.map(reviewRepository.save(existingReview), ReviewResDto.class);
    }

    @Override
    public void delete(UUID id) {
        reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review Not found with this: " + id));
        reviewRepository.deleteById(id);
    }
}
