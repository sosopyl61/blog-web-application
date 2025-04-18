package com.rymtsou.service.impl;

import com.rymtsou.model.response.GetUserResponse;
import com.rymtsou.repository.UserRepository;
import com.rymtsou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<List<GetUserResponse>> getAllUsers() {
        return Optional.of(userRepository.findAll().stream()
                .map(user -> GetUserResponse.builder()
                        .firstname(user.getFirstname())
                        .secondName(user.getSecondName())
                        .email(user.getEmail())
                        .age(user.getAge())
                        .sex(user.getSex())
                        .build()
                ).toList());
    }
}
