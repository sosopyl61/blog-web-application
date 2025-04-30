package com.rymtsou.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequestDto {
    @NotBlank
    private String username;

    private String firstname;
    private String secondName;
    private String email;
    private Integer age;
    private String sex;
}
