package com.rymtsou.model.request;

import lombok.Data;

@Data
public class UpdatePostRequestDto {
    private Long id;
    private String title;
    private String content;
}
