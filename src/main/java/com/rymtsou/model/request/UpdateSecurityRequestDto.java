package com.rymtsou.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateSecurityRequestDto {
    @NotBlank(message = "Login required.")
    @Size(min = 3, max = 20, message = "Login must be 3-20 characters.")
    private String currentLogin;

    @Size(min = 3, max = 20, message = "Login must be 3-20 characters.")
    private String newLogin;
    private String newPassword;
}
