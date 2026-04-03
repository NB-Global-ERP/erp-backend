package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.Instant;

public record GroupPatchRequest(
        @Positive(message = "Course ID must be positive")
        Integer courseId,

        Instant dateBegin,
        Instant dateEnd,

        @DecimalMin(value = "0.00", message = "Price must be non-negative")
        BigDecimal pricePerPerson,

        @PositiveOrZero(message = "Participant count must be non-negative")
        Integer participantCount,

        @DecimalMin(value = "0.00", message = "Price must be non-negative")
        BigDecimal groupPrice,

        @Positive(message = "Course completion ID must be positive")
        Integer courseCompletionId,

        @PositiveOrZero(message = "Average progress must be non-negative")
        Float averageProgress,

        @Positive(message = "Specification ID must be positive")
        Integer specificationId
) {}
