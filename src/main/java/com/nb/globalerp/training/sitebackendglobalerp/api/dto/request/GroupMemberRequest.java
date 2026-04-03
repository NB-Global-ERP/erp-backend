package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record GroupMemberRequest(
        @NotNull(message = "Student ID is required")
        @Positive(message = "Student ID must be positive")
        Integer studentId,

        @NotNull(message = "Group ID is required")
        @Positive(message = "Group ID must be positive")
        Integer groupId,

        @NotNull(message = "Completion percent is required")
        @DecimalMin(value = "0.00", message = "Completion percent must be at least 0")
        @DecimalMax(value = "1.00", message = "Completion percent must be at most 1")
        BigDecimal completionPercent
) {}
