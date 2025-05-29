package com.rymtsou.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePostRequestDto {
    @NotBlank(message = "Title required.")
    @Size(min = 1, max = 50, message = "Title must be 1-50 characters.")
    private String title;

    @NotBlank(message = "Content required.")
    @Size(min = 1, max = 500, message = "Content must be 1-500 characters.")
    private String content;

    @Size(min = 3, max = 20, message = "Username must be 3-20 characters.")
    private String username;
}
