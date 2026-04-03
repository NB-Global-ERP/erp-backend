package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record GroupMemberPatchRequest(
        @Positive(message = "Student ID must be positive")
        Integer studentId,

        @Positive(message = "Group ID must be positive")
        Integer groupId,

        @DecimalMin(value = "0.00", message = "Completion percent must be at least 0")
        @DecimalMax(value = "1.00", message = "Completion percent must be at most 1")
        BigDecimal completionPercent
) {}
