package com.rymtsou.service.impl;

import com.rymtsou.exception.ExistingUserException;
import com.rymtsou.exception.LoginUsedException;
import com.rymtsou.exception.SecurityNotFoundException;
import com.rymtsou.model.domain.Role;
import com.rymtsou.model.domain.Security;
import com.rymtsou.model.domain.User;
import com.rymtsou.model.request.LoginRequestDto;
import com.rymtsou.model.request.RegistrationRequestDto;
import com.rymtsou.model.request.UpdateSecurityRequestDto;
import com.rymtsou.model.response.RegistrationResponseDto;
import com.rymtsou.model.response.UpdateSecurityResponseDto;
import com.rymtsou.repository.SecurityRepository;
import com.rymtsou.repository.UserRepository;
import com.rymtsou.security.JwtUtil;
import com.rymtsou.service.SecurityService;
import com.rymtsou.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final SecurityRepository securityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthUtil authUtil;

    @Autowired
    public SecurityServiceImpl(SecurityRepository securityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthUtil authUtil) {
        this.securityRepository = securityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authUtil = authUtil;
    }

    @Override
    public Optional<String> generateToken(LoginRequestDto loginRequestDto) {
        Optional<Security> securityOptional = securityRepository.findByLogin(loginRequestDto.getLogin());
        if (securityOptional.isPresent() && passwordEncoder.matches(loginRequestDto.getPassword(), securityOptional.get().getPassword())) {
            return jwtUtil.generateJwtToken(loginRequestDto.getLogin());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Security> getSecurityById(Long id) {
        return securityRepository.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<RegistrationResponseDto> registration(RegistrationRequestDto requestDto) throws ExistingUserException {
        if (securityRepository.existsByLogin(requestDto.getLogin())) {
            throw new ExistingUserException(requestDto.getLogin());
        }

        User user = User.builder()
                .username(requestDto.getUsername())
                .firstname(requestDto.getFirstname())
                .secondName(requestDto.getSecondName())
                .email(requestDto.getEmail())
                .age(requestDto.getAge())
                .sex(requestDto.getSex())
                .build();
        User userRegistered = userRepository.save(user);

        Security security = Security.builder()
                .login(requestDto.getLogin())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(Role.USER)
                .userId(userRegistered.getId())
                .build();
        securityRepository.save(security);

        RegistrationResponseDto responseDto = RegistrationResponseDto.builder()
                .username(userRegistered.getUsername())
                .firstname(userRegistered.getFirstname())
                .secondName(userRegistered.getSecondName())
                .email(userRegistered.getEmail())
                .age(userRegistered.getAge())
                .sex(userRegistered.getSex())
                .build();

        return Optional.of(responseDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<UpdateSecurityResponseDto> updateSecurity(UpdateSecurityRequestDto requestDto) {
        if (!authUtil.canAccessSecurityByLogin(requestDto.getCurrentLogin())) {
            throw new AccessDeniedException("Access denied, username: " + requestDto.getCurrentLogin());
        }

        Optional<Security> securityOptional = securityRepository.findByLogin(requestDto.getCurrentLogin());
        if (securityOptional.isEmpty()) {
            throw new SecurityNotFoundException("Security not found with login: " + requestDto.getCurrentLogin());
        }

        if (requestDto.getNewLogin() != null && !requestDto.getNewLogin().equals(securityOptional.get().getLogin())) {
            if (securityRepository.existsByLogin(requestDto.getNewLogin())) {
                throw new LoginUsedException(requestDto.getNewLogin());
            }
            securityOptional.get().setLogin(requestDto.getNewLogin());
        }

        if (requestDto.getNewPassword() != null) {
            securityOptional.get().setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        }

        Security updatedSecurity = securityRepository.save(securityOptional.get());
        return Optional.of(new UpdateSecurityResponseDto(updatedSecurity.getLogin()));
    }
}
