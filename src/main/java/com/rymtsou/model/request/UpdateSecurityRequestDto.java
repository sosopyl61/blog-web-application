package com.rymtsou.model.request;

import lombok.Data;

@Data
public class UpdateSecurityRequestDto {
    private String currentLogin;
    private String newLogin;
    private String newPassword;
}
