package com.rymtsou.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePostRequestDto {
    @NotNull(message = "ID required.")
    private Long id;

    @Size(min = 1, max = 500, message = "Content must be 1-500 characters.")
    private String title;

    @Size(min = 3, max = 20, message = "Username must be 3-20 characters.")
    private String content;
}
