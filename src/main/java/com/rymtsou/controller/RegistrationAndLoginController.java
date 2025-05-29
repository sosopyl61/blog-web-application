package com.rymtsou.controller;

import com.rymtsou.exception.ExistingUserLoginException;
import com.rymtsou.exception.ExistingUsernameException;
import com.rymtsou.model.request.LoginRequestDto;
import com.rymtsou.model.request.RegistrationRequestDto;
import com.rymtsou.model.response.LoginResponseDto;
import com.rymtsou.model.response.RegistrationResponseDto;
import com.rymtsou.service.impl.SecurityServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(summary = "Sign up",
            description = "Making a registration of the user.")
    @ApiResponses(value = {
            @ApiResponse(description = "Registration was completed.", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = RegistrationResponseDto.class))),
            @ApiResponse(description = "Registration was failed.", responseCode = "409", content = @Content)
    })
    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDto> registration(@RequestBody @Valid RegistrationRequestDto requestDto) throws ExistingUserLoginException, ExistingUsernameException {
        Optional<RegistrationResponseDto> userRegistered = securityService.registration(requestDto);
        if (userRegistered.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userRegistered.get(), HttpStatus.CREATED);
    }

    @Operation(summary = "Login",
            description = "Login the user using login and password.")
    @ApiResponses(value = {
            @ApiResponse(description = "Login was completed.", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(description = "Login was failed.", responseCode = "401", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> generateToken(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        Optional<String> token = securityService.generateToken(loginRequestDto);
        if (token.isPresent()) {
            return new ResponseEntity<>(new LoginResponseDto(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
