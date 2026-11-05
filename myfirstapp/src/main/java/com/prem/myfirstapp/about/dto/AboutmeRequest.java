package com.prem.myfirstapp.about.dto;

import jakarta.validation.constraints.*;

public record AboutmeRequest(
        @NotNull Long profileId,
        @NotBlank @Size(max = 150) String sectionTitle,
        @NotBlank String description,
        @NotNull @PositiveOrZero Integer displayOrder,
        Boolean isActive) {}