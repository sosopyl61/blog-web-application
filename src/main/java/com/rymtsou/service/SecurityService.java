package com.rymtsou.service;

import com.rymtsou.exception.ExistingUserException;
import com.rymtsou.model.domain.Security;
import com.rymtsou.model.request.AuthRequestDto;
import com.rymtsou.model.request.RegistrationRequestDto;
import com.rymtsou.model.response.RegistrationResponseDto;

import java.util.Optional;

public interface SecurityService {
    Optional<Security> getSecurityById(Long id);
    Optional<RegistrationResponseDto> registration(RegistrationRequestDto requestDto) throws ExistingUserException;
    Boolean canAccessUser(String username);
    Optional<String> generateToken(AuthRequestDto requestDto);
}
