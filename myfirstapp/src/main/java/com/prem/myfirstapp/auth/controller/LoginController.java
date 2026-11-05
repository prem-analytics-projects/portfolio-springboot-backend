package com.prem.myfirstapp.auth.controller;

import com.prem.myfirstapp.auth.dto.LoginRequest;
import com.prem.myfirstapp.auth.dto.LoginResponse;
import com.prem.myfirstapp.auth.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService service;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        return service.login(req);
    }
}
