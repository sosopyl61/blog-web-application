package com.rymtsou.service;

import com.rymtsou.model.domain.User;
import com.rymtsou.model.response.GetUserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<List<GetUserResponse>> getAllUsers();
    Optional<User> getUserById(Long id);
    Optional<GetUserResponse> getUserByUsername(String username);
    Optional<GetUserResponse> updateUser(User user);
    Boolean deleteUser(Long id);
}
