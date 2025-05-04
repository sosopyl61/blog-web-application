package com.rymtsou.model.request;

import lombok.Data;

@Data
public class CreatePostRequestDto {
    private String title;
    private String content;
    private String username;
}
