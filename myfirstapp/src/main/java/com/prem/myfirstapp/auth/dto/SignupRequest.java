package com.prem.myfirstapp.auth.dto;

import jakarta.validation.constraints.*;

public record SignupRequest(
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Size(max = 100) String lastName,
        @NotBlank @Email @Size(max = 254) String email,
        @Pattern(regexp = "^[0-9+\\- ]{7,20}$", message = "Invalid mobile number") String mobile,
        @NotBlank @Size(min = 8, max = 72, message = "Password must be 8 to 72 characters") String password
) {}
