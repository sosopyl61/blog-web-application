package com.rymtsou.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCommentResponseDto {
    private String commentText;
    private String author;
}
