package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.Instant;

public record GroupPatchRequest(
        @Schema(example = "1")
        @Positive(message = "Course ID must be positive")
        Integer courseId,

        @Schema(example = "2025-09-01T09:00:00Z")
        Instant dateBegin,

//        @Schema(example = "2025-10-01T18:00:00Z")
//        Instant dateEnd,

//        @Schema(example = "15000.00")
//        @DecimalMin(value = "0.00", message = "Price must be non-negative")
//        BigDecimal pricePerPerson,

//        @Schema(example = "8")
//        @PositiveOrZero(message = "Participant count must be non-negative")
//        Integer participantCount,

//        @Schema(example = "120000.00")
//        @DecimalMin(value = "0.00", message = "Price must be non-negative")
//        BigDecimal groupPrice,

        @Schema(example = "1")
        @Positive(message = "Course completion ID must be positive")
        Integer courseCompletionId,

        @Schema(example = "1")
        @Positive(message = "Specification ID must be positive")
        Integer specificationId
) {}
