package com.rymtsou.service;

import com.rymtsou.exception.ExistingUserException;
import com.rymtsou.model.domain.Security;
import com.rymtsou.model.request.RegistrationRequest;
import com.rymtsou.model.response.RegistrationResponse;

import java.util.Optional;

public interface SecurityService {
    Optional<Security> getSecurityById(Long id);
    Optional<RegistrationResponse> registration(RegistrationRequest requestDto) throws ExistingUserException;
}
