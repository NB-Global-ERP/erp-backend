package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;

public record GroupRequest(
        @Schema(example = "1")
        @NotNull(message = "Course ID is required")
        @Positive(message = "Course ID must be positive")
        Integer courseId,

        @Schema(example = "2025-09-01T09:00:00Z")
        @NotNull(message = "Start date is required")
        Instant dateBegin,

        @Schema(example = "2025-10-01T18:00:00Z")
        @NotNull(message = "End date is required")
        Instant dateEnd,

        @Schema(example = "15000.00")
        @NotNull(message = "Price per person is required")
        @DecimalMin(value = "0.00", message = "Price must be non-negative")
        BigDecimal pricePerPerson,

        @Schema(example = "120000.00")
        @NotNull(message = "Group price is required")
        @DecimalMin(value = "0.00", message = "Price must be non-negative")
        BigDecimal groupPrice,

        @Schema(example = "1")
        @NotNull(message = "Course completion ID is required")
        @Positive(message = "Course completion ID must be positive")
        Integer courseCompletionId,

        @Schema(example = "1")
        @NotNull(message = "Specification ID is required")
        @Positive(message = "Specification ID must be positive")
        Integer specificationId
) {}
