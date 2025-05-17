package com.rymtsou.model.request;

import lombok.Data;

@Data
public class UpdateCommentRequestDto {
    private Long id;
    private String commentText;
}
