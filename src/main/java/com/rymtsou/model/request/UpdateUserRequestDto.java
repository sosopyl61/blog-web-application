package com.rymtsou.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequestDto {
    private Long id;
    private String firstname;
    private String secondName;
    private String email;
    private Integer age;
    private String sex;
}
