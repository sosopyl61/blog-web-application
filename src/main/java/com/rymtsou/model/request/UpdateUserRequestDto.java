package com.rymtsou.model.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequestDto {
    @NotNull(message = "ID required.")
    private Long id;

    @Size(min = 2, max = 50, message = "Firstname must be 2-50 characters.")
    private String firstname;

    @Size(min = 2, max = 50, message = "Second name must be 2-50 characters.")
    private String secondName;

    @Email(message = "Invalid email.")
    private String email;

    @Digits(integer = 3, fraction = 0, message = "Age must be with 3 digits.")
    private Integer age;

    @Size(min = 1, max = 1, message = "Sex must be 1 character.")
    private String sex;
}
