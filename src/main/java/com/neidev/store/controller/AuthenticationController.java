package com.neidev.store.controller;

import com.neidev.store.domain.core.user.model.User;
import com.neidev.store.security.infra.service.JwtTokenService;
import com.neidev.store.security.record.LoginRecord;
import com.neidev.store.security.record.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private AuthenticationManager manager;
    private JwtTokenService tokenService;

    public AuthenticationController(AuthenticationManager manager, JwtTokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRecord data) {
        var usernamePasswordToken = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = manager.authenticate(usernamePasswordToken);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok().body(new LoginResponse(token));
    }


}
