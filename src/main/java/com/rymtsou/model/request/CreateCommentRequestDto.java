package com.rymtsou.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCommentRequestDto {
    @NotNull(message = "Post's ID required.")
    private Long postId;

    @NotBlank(message = "Comment text required.")
    @Size(min = 1, max = 200, message = "Comment must be 1-500 characters.")
    private String commentText;

    @Size(min = 3, max = 20, message = "Username must be 3-20 characters.")
    private String username;
}