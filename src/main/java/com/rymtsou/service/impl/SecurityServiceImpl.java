package com.rymtsou.service.impl;

import com.rymtsou.exception.ExistingUserException;
import com.rymtsou.model.domain.Role;
import com.rymtsou.model.domain.Security;
import com.rymtsou.model.domain.User;
import com.rymtsou.model.request.AuthRequestDto;
import com.rymtsou.model.request.RegistrationRequestDto;
import com.rymtsou.model.response.RegistrationResponseDto;
import com.rymtsou.repository.SecurityRepository;
import com.rymtsou.repository.UserRepository;
import com.rymtsou.security.JwtUtil;
import com.rymtsou.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    public SecurityServiceImpl(SecurityRepository securityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.securityRepository = securityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Optional<String> generateToken(AuthRequestDto authRequestDto) {
        Optional<Security> securityOptional = securityRepository.findByLogin(authRequestDto.getLogin());
        if (securityOptional.isPresent() && passwordEncoder.matches(authRequestDto.getPassword(), securityOptional.get().getPassword())) {
            return jwtUtil.generateJwtToken(authRequestDto.getLogin());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Security> getSecurityById(Long id) {
        return securityRepository.findById(id);
    }

    @Override
    public Boolean canAccessUser(String username) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Security> optionalSecurity = securityRepository.findByLogin(login);
        if (optionalSecurity.isEmpty()) {
            return false;
        }
        Security security = optionalSecurity.get();
        if (security.getRole().equals(Role.ADMIN)) {
            return true;
        }

        Optional<User> requestedUser = userRepository.findByUsername(username);
        return requestedUser.isPresent() && security.getUserId().equals(requestedUser.get().getId());
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
}
