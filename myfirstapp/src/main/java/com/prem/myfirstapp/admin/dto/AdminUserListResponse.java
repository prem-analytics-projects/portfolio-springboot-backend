package com.prem.myfirstapp.admin.dto;

import java.time.OffsetDateTime;
import java.util.Set;

public record AdminUserListResponse(
        Long userId,
        String firstName,
        String lastName,
        String email,
        String mobile,
        OffsetDateTime emailVerifiedAt,
        String approvalStatus,
        Long approvedBy,
        OffsetDateTime approvedAt,
        OffsetDateTime createdAt,
        Set<String> roles
) {}