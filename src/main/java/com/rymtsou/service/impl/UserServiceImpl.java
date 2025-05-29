package com.rymtsou.service.impl;

import com.rymtsou.exception.EntityNotFoundException;
import com.rymtsou.model.domain.User;
import com.rymtsou.model.request.UpdateUserRequestDto;
import com.rymtsou.model.response.GetUserResponseDto;
import com.rymtsou.model.response.GetUsersResponseDto;
import com.rymtsou.repository.UserRepository;
import com.rymtsou.service.UserService;
import com.rymtsou.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthUtil authUtil;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthUtil authUtil) {
        this.userRepository = userRepository;
        this.authUtil = authUtil;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<GetUserResponseDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> GetUserResponseDto.builder()
                        .username(user.getUsername())
                        .firstname(user.getFirstname())
                        .secondName(user.getSecondName())
                        .email(user.getEmail())
                        .age(user.getAge())
                        .sex(user.getSex())
                        .posts(user.getPosts())
                        .build()
                );
    }

    @Override
    public Optional<List<GetUsersResponseDto>> getAllUsers() {
        return Optional.of(userRepository.findAll().stream()
                .map(user -> GetUsersResponseDto.builder()
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
    @Transactional(rollbackFor = Exception.class)
    public Optional<GetUsersResponseDto> updateUser(UpdateUserRequestDto dto) {
        if (!authUtil.canAccessUser(dto.getId())) {
            throw new AccessDeniedException("You do not have permission to update this user.");
        }

        Optional<User> user = userRepository.findById(dto.getId());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + dto.getId());
        }

        if (dto.getFirstname() != null) user.get().setFirstname(dto.getFirstname());
        if (dto.getSecondName() != null) user.get().setSecondName(dto.getSecondName());
        if (dto.getEmail() != null) user.get().setEmail(dto.getEmail());
        if (dto.getAge() != null) user.get().setAge(dto.getAge());
        if (dto.getSex() != null) user.get().setSex(dto.getSex());

        User updatedUser = userRepository.save(user.get());

        return Optional.of(GetUsersResponseDto.builder()
                .username(updatedUser.getUsername())
                .firstname(updatedUser.getFirstname())
                .secondName(updatedUser.getSecondName())
                .email(updatedUser.getEmail())
                .age(updatedUser.getAge())
                .sex(updatedUser.getSex())
                .build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteUser(Long id) {
        if (authUtil.canAccessUser(id)) {
            userRepository.deleteById(id);
            return !userRepository.existsById(id);
        }
        throw new AccessDeniedException("Access denied, id: " + id);
    }
}
