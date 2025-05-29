package com.rymtsou.model.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequestDto {
    @NotBlank(message = "Login required.")
    @Size(min = 3, max = 20, message = "Login must be 3-20 characters.")
    private String login;

    @NotBlank(message = "Password required.")
    private String password;

    @NotBlank(message = "Username required.")
    @Size(min = 3, max = 20, message = "Username must be 3-20 characters.")
    private String username;

    @NotBlank(message = "Firstname required.")
    @Size(min = 2, max = 50, message = "Firstname must be 2-50 characters.")
    private String firstname;

    @NotBlank(message = "Second name required.")
    @Size(min = 2, max = 50, message = "Second name must be 2-50 characters.")
    private String secondName;

    @NotNull
    @Email(message = "Invalid email.")
    private String email;

    @NotNull
    @Digits(integer = 3, fraction = 0, message = "Age must be with 3 digits.")
    private Integer age;

    @NotNull
    @Size(min = 1, max = 1, message = "Sex must be 1 character.")
    private String sex;
}
