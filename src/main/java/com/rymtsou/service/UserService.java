package com.rymtsou.service;

import com.rymtsou.model.response.GetUserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<List<GetUserResponse>> getAllUsers();
}
