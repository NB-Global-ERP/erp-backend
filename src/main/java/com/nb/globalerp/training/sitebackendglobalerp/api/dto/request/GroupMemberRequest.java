package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record GroupMemberRequest(
        @Schema(example = "1")
        @NotNull(message = "Student ID is required")
        @Positive(message = "Student ID must be positive")
        Integer studentId,

        @Schema(example = "1")
        @NotNull(message = "Group ID is required")
        @Positive(message = "Group ID must be positive")
        Integer groupId,

        @Schema(example = "0.75")
        @NotNull(message = "Completion percent is required")
        @DecimalMin(value = "0.00", message = "Completion percent must be at least 0")
        @DecimalMax(value = "1.00", message = "Completion percent must be at most 1")
        BigDecimal completionPercent
) {}
