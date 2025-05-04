package com.rymtsou.model.request;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String login;
    private String password;
}
