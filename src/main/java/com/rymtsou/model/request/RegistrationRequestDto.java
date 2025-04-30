package com.rymtsou.model.request;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String login;
    private String password;

    private String username;
    private String firstname;
    private String secondName;
    private String email;
    private Integer age;
    private String sex;
}
