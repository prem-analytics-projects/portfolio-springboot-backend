package com.prem.myfirstapp.about.dto;

public record AboutmeResponse(
        Long profileId,
        String sectionTitle,
        String description,
        Integer displayOrder,
        Boolean isActive) {}