package com.example.demo.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ReviewReqDto {

    private UUID id;

//    @NotBlank(message = "title should be not blank")
    private String title;

//    @NotBlank(message = "message should be not blank")
    private String message;

//    @NotNull(message = "Person Id should be not Nul")
    private UUID person_id;
}
