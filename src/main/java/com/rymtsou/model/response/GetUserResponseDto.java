package com.rymtsou.model.response;

import com.rymtsou.model.domain.Post;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetUserResponseDto {
    private String username;
    private String firstname;
    private String secondName;
    private String email;
    private Integer age;
    private String sex;
    private List<Post> posts;
}
