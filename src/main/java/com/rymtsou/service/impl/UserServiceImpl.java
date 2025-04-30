package com.rymtsou.service.impl;

import com.rymtsou.model.domain.User;
import com.rymtsou.model.request.DeleteUserRequestDto;
import com.rymtsou.model.request.FindUserRequestDto;
import com.rymtsou.model.request.UpdateUserRequestDto;
import com.rymtsou.model.response.GetUserResponseDto;
import com.rymtsou.repository.UserRepository;
import com.rymtsou.service.SecurityService;
import com.rymtsou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecurityService securityService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<GetUserResponseDto> getUserByUsername(FindUserRequestDto dto) {
        return userRepository.findByUsername(dto.getUsername())
                .map(user -> GetUserResponseDto.builder()
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
    public Optional<List<GetUserResponseDto>> getAllUsers() {
        return Optional.of(userRepository.findAll().stream()
                .map(user -> GetUserResponseDto.builder()
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
    public Optional<GetUserResponseDto> updateUser(UpdateUserRequestDto dto) {
        if (!securityService.canAccessUser(dto.getUsername())) {
            throw new AccessDeniedException("Access denied, username: " + dto.getUsername());
        }

        Optional<User> optionalUser = userRepository.findByUsername(dto.getUsername());
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        User user = optionalUser.get();
        if (dto.getFirstname() != null) user.setFirstname(dto.getFirstname());
        if (dto.getSecondName() != null) user.setSecondName(dto.getSecondName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getAge() != null) user.setAge(dto.getAge());
        if (dto.getSex() != null) user.setSex(dto.getSex());

        User updatedUser = userRepository.save(user);

        return Optional.of(GetUserResponseDto.builder()
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
    public Boolean deleteUser(DeleteUserRequestDto dto) {
        if (securityService.canAccessUser(dto.getUsername())) {
            userRepository.deleteByUsername(dto.getUsername());
            return !userRepository.existsByUsername(dto.getUsername());
        }
        throw new AccessDeniedException("Access denied, username: " + dto.getUsername());
    }
}
