package com.rymtsou.service.impl;

import com.rymtsou.exception.ExistingUserException;
import com.rymtsou.model.domain.Role;
import com.rymtsou.model.domain.Security;
import com.rymtsou.model.domain.User;
import com.rymtsou.model.request.RegistrationRequest;
import com.rymtsou.model.response.RegistrationResponse;
import com.rymtsou.repository.SecurityRepository;
import com.rymtsou.repository.UserRepository;
import com.rymtsou.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final SecurityRepository securityRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Security> getSecurityById(Long id) {
        //TODO сделать проверку на доступ к этим данным (только админ)
        //TODO иначе выбросить исключение AccessDenied
        return securityRepository.findById(id);
    }

    @Autowired
    public SecurityServiceImpl(SecurityRepository securityRepository, UserRepository userRepository) {
        this.securityRepository = securityRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<RegistrationResponse> registration(RegistrationRequest requestDto) throws ExistingUserException {
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
                .password(requestDto.getPassword())
                .role(Role.USER)
                .userId(userRegistered.getId())
                .build();
        securityRepository.save(security);

        RegistrationResponse responseDto = RegistrationResponse.builder()
                .username(userRegistered.getUsername())
                .firstname(userRegistered.getFirstname())
                .secondName(userRegistered.getSecondName())
                .email(userRegistered.getEmail())
                .age(userRegistered.getAge())
                .sex(userRegistered.getSex())
                .build();

        return Optional.of(responseDto);
    }
}
