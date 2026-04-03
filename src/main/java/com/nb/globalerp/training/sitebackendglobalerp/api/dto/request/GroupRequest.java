package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.Instant;

public record GroupRequest(
        @NotNull(message = "Course ID is required")
        @Positive(message = "Course ID must be positive")
        Integer courseId,

        @NotNull(message = "Start date is required")
        Instant dateBegin,

        @NotNull(message = "End date is required")
        Instant dateEnd,

        @NotNull(message = "Price per person is required")
        @DecimalMin(value = "0.00", message = "Price must be non-negative")
        BigDecimal pricePerPerson,

        @PositiveOrZero(message = "Participant count must be non-negative")
        int participantCount,

        @NotNull(message = "Group price is required")
        @DecimalMin(value = "0.00", message = "Price must be non-negative")
        BigDecimal groupPrice,

        @NotNull(message = "Course completion ID is required")
        @Positive(message = "Course completion ID must be positive")
        Integer courseCompletionId,

        @PositiveOrZero(message = "Average progress must be non-negative")
        float averageProgress,

        @NotNull(message = "Specification ID is required")
        @Positive(message = "Specification ID must be positive")
        Integer specificationId
) {}
