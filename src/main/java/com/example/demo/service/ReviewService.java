package com.example.demo.service;

import com.example.demo.model.request.ReviewReqDto;
import com.example.demo.model.response.ReviewResDto;

import java.util.UUID;

public interface ReviewService extends BaseService<ReviewReqDto, UUID, ReviewResDto>{
}
