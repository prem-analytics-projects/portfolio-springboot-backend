package com.prem.myfirstapp.auth.controller;

import com.prem.myfirstapp.auth.dto.SignupRequest;
import com.prem.myfirstapp.auth.service.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class SignupController {
    private final SignupService service;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody SignupRequest req) {
        service.signup(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Check your email to verify your account"));
    }
}
