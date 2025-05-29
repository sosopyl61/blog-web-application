package com.rymtsou.service;

import com.rymtsou.exception.ExistingUserLoginException;
import com.rymtsou.exception.ExistingUsernameException;
import com.rymtsou.model.domain.Security;
import com.rymtsou.model.request.LoginRequestDto;
import com.rymtsou.model.request.RegistrationRequestDto;
import com.rymtsou.model.request.UpdateSecurityRequestDto;
import com.rymtsou.model.response.RegistrationResponseDto;
import com.rymtsou.model.response.UpdateSecurityResponseDto;

import java.util.Optional;

public interface SecurityService {
    Optional<RegistrationResponseDto> registration(RegistrationRequestDto requestDto) throws ExistingUserLoginException, ExistingUsernameException;
    Optional<String> generateToken(LoginRequestDto requestDto);
    Optional<Security> getSecurityById(Long id);
    Optional<UpdateSecurityResponseDto> updateSecurity(UpdateSecurityRequestDto requestDto) throws ExistingUserLoginException;
}
