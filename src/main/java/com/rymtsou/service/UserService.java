package com.rymtsou.service;

import com.rymtsou.model.domain.User;
import com.rymtsou.model.request.DeleteUserRequestDto;
import com.rymtsou.model.request.FindUserRequestDto;
import com.rymtsou.model.request.UpdateUserRequestDto;
import com.rymtsou.model.response.GetUserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<List<GetUserResponseDto>> getAllUsers();
    Optional<User> getUserById(Long id);
    Optional<GetUserResponseDto> getUserByUsername(FindUserRequestDto dto);
    Optional<GetUserResponseDto> updateUser(UpdateUserRequestDto dto);
    Boolean deleteUser(DeleteUserRequestDto dto);
}
