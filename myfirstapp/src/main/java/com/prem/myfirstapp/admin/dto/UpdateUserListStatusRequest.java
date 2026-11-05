package com.prem.myfirstapp.admin.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateUserListStatusRequest(
        @NotNull
        @Pattern(regexp = "PENDING|APPROVED|REJECTED",
                 message = "Status must be PENDING, APPROVED or REJECTED")
        String status
) {}