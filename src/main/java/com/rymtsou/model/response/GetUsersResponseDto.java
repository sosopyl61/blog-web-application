package com.rymtsou.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUsersResponseDto {
    private String username;
    private String firstname;
    private String secondName;
    private String email;
    private Integer age;
    private String sex;
}
