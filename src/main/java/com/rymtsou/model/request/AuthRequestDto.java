package com.rymtsou.model.request;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String login;
    private String password;
}
