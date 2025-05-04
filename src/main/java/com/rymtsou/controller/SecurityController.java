package com.rymtsou.controller;

import com.rymtsou.model.domain.Security;
import com.rymtsou.model.request.UpdateSecurityRequestDto;
import com.rymtsou.model.response.UpdateSecurityResponseDto;
import com.rymtsou.service.SecurityService;
import com.rymtsou.service.impl.SecurityServiceImpl;
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

    @GetMapping("/{id}")
    public ResponseEntity<Security> getSecurityById(@PathVariable("id") Long id) {
        Optional<Security> security = securityService.getSecurityById(id);
        if (security.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(security.get(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UpdateSecurityResponseDto> updateSecurity(@RequestBody UpdateSecurityRequestDto dto) {
        Optional<UpdateSecurityResponseDto> updatedSecurity = securityService.updateSecurity(dto);
        if (updatedSecurity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(updatedSecurity.get(), HttpStatus.OK);
    }
}
