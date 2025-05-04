package com.rymtsou.service;

import com.rymtsou.model.domain.User;
import com.rymtsou.model.request.DeleteByIdRequestDto;
import com.rymtsou.model.request.FindUserRequestDto;
import com.rymtsou.model.request.UpdateUserRequestDto;
import com.rymtsou.model.response.GetUserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    Optional<GetUserResponseDto> getUserByUsername(FindUserRequestDto requestDto);
    Optional<List<GetUserResponseDto>> getAllUsers();
    Optional<GetUserResponseDto> updateUser(UpdateUserRequestDto requestDto);
    Boolean deleteUser(DeleteByIdRequestDto requestDto);
}
