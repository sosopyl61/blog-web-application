package com.rymtsou.controller;

import com.rymtsou.exception.ExistingUserException;
import com.rymtsou.model.request.AuthRequestDto;
import com.rymtsou.model.request.RegistrationRequestDto;
import com.rymtsou.model.response.AuthResponseDto;
import com.rymtsou.model.response.RegistrationResponseDto;
import com.rymtsou.service.impl.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RegistrationAndLoginController {

    private final SecurityServiceImpl securityService;

    @Autowired
    public RegistrationAndLoginController(SecurityServiceImpl securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDto> registration(@RequestBody RegistrationRequestDto requestDto) throws ExistingUserException {
        Optional<RegistrationResponseDto> userRegistered = securityService.registration(requestDto);
        if (userRegistered.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userRegistered.get(), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> generateToken(@RequestBody AuthRequestDto authRequestDto) {
        Optional<String> token = securityService.generateToken(authRequestDto);
        if (token.isPresent()) {
            return new ResponseEntity<>(new AuthResponseDto(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
