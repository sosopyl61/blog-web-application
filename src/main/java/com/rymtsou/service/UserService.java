package com.rymtsou.service;

import com.rymtsou.model.domain.User;
import com.rymtsou.model.request.UpdateUserRequestDto;
import com.rymtsou.model.response.GetUserResponseDto;
import com.rymtsou.model.response.GetUsersResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    Optional<GetUserResponseDto> getUserByUsername(String username);
    Optional<List<GetUsersResponseDto>> getAllUsers();
    Optional<GetUsersResponseDto> updateUser(UpdateUserRequestDto requestDto);
    Boolean deleteUser(Long id);
}
