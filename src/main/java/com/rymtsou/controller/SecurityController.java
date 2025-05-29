package com.rymtsou.controller;

import com.rymtsou.exception.ExistingUserLoginException;
import com.rymtsou.model.domain.Security;
import com.rymtsou.model.request.UpdateSecurityRequestDto;
import com.rymtsou.model.response.UpdateSecurityResponseDto;
import com.rymtsou.service.SecurityService;
import com.rymtsou.service.impl.SecurityServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/security")
public class SecurityController {

    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityServiceImpl securityService) {
        this.securityService = securityService;
    }

    @Operation(summary = "Get security by id",
            description = "Getting user's security info by his/her id.")
    @ApiResponses(value = {
            @ApiResponse(description = "Security was received.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Security.class))),
            @ApiResponse(description = "Security was not found.", responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Security> getSecurityById(@PathVariable("id") @Parameter(description = "Security's id") Long id) {
        Optional<Security> security = securityService.getSecurityById(id);
        if (security.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(security.get(), HttpStatus.OK);
    }

    @Operation(summary = "Edit security",
            description = "Editing user's security info by his/her current login.")
    @ApiResponses(value = {
            @ApiResponse(description = "Security was updated.", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = UpdateSecurityResponseDto.class))),
            @ApiResponse(description = "Security updating was failed.", responseCode = "409", content = @Content)
    })
    @PutMapping
    public ResponseEntity<UpdateSecurityResponseDto> updateSecurity(@RequestBody @Valid UpdateSecurityRequestDto dto) throws ExistingUserLoginException {
        Optional<UpdateSecurityResponseDto> updatedSecurity = securityService.updateSecurity(dto);
        if (updatedSecurity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(updatedSecurity.get(), HttpStatus.OK);
    }
}
