package com.rymtsou.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePostResponseDto {
    private String title;
    private String content;
    private String author;
}
