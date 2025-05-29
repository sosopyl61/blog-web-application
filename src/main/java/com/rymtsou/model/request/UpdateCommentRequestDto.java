package com.rymtsou.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCommentRequestDto {
    @NotNull(message = "ID required.")
    private Long id;

    @NotBlank(message = "Comment text required.")
    @Size(min = 1, max = 200, message = "Comment must be 1-500 characters.")
    private String commentText;
}
