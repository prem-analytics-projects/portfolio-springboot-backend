package com.prem.myfirstapp.auth.controller;

import com.prem.myfirstapp.auth.dto.VerifyEmailRequest;
import com.prem.myfirstapp.auth.service.VerifyEmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class VerifyEmailController {
    private final VerifyEmailService service;

    // @PostMapping("/verify-email")
    // public Map<String, String> verify(@Valid @RequestBody VerifyEmailRequest req) {
    //     service.verify(req.token());
    //     return Map.of("message", "Email verified. Awaiting admin approval.");
    // }
    @PostMapping("/verify-email")
public Map<String, String> verify(
        @Valid @RequestBody VerifyEmailRequest req) {

    System.out.println("========== VERIFY CONTROLLER REACHED ==========");

    service.verify(req.token());

    System.out.println("========== VERIFY COMPLETED ==========");

    return Map.of(
            "message",
            "Email verified. Awaiting admin approval."
    );
}
}
