package com.prem.myfirstapp.auth.dto;

import java.util.List;

public record LoginResponse(String token, String firstName, List<String> roles) {}
