package com.rymtsou.model.request;

import lombok.Data;

@Data
public class CreateCommentRequestDto {
    private Long postId;
    private String commentText;
    private String username;
}