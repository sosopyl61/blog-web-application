package com.rymtsou.model.request;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String login;
    private String password;

    private String firstname;
    private String secondName;
    private String email;
    private Integer age;
    private String sex;
}
