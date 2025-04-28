package com.rymtsou.service.impl;

import com.rymtsou.model.domain.User;
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
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<GetUserResponse> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> GetUserResponse.builder()
                        .username(user.getUsername())
                        .firstname(user.getFirstname())
                        .secondName(user.getSecondName())
                        .email(user.getEmail())
                        .age(user.getAge())
                        .sex(user.getSex())
                        .build()
                );
    }

    @Override
    public Optional<List<GetUserResponse>> getAllUsers() {
        return Optional.of(userRepository.findAll().stream()
                .map(user -> GetUserResponse.builder()
                        .username(user.getUsername())
                        .firstname(user.getFirstname())
                        .secondName(user.getSecondName())
                        .email(user.getEmail())
                        .age(user.getAge())
                        .sex(user.getSex())
                        .build()
                ).toList());
    }

    @Override
    public Optional<GetUserResponse> updateUser(User user) {
        return Optional.of(userRepository.save(user))
                .map(requestUser -> GetUserResponse.builder()
                        .username(requestUser.getUsername())
                        .firstname(requestUser.getFirstname())
                        .secondName(requestUser.getSecondName())
                        .email(requestUser.getEmail())
                        .age(requestUser.getAge())
                        .sex(requestUser.getSex())
                        .build());
    }

    @Override
    public Boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }
}
