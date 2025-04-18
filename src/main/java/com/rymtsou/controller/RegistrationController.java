package com.rymtsou.controller;

import com.rymtsou.exception.ExistingUserException;
import com.rymtsou.model.request.RegistrationRequest;
import com.rymtsou.model.response.RegistrationResponse;
import com.rymtsou.service.impl.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RegistrationController {

    private final SecurityServiceImpl securityService;

    @Autowired
    public RegistrationController(SecurityServiceImpl securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> registration(@RequestBody RegistrationRequest requestDto) throws ExistingUserException {
        Optional<RegistrationResponse> userRegistered = securityService.registration(requestDto);
        if (userRegistered.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userRegistered.get(), HttpStatus.CREATED);
    }
}
