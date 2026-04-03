package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CoursePatchRequest(
        @Schema(example = "Java для начинающих")
        String name,

        @Schema(example = "Базовый курс по Java с нуля")
        String description,

        @Schema(example = "30")
        @Positive(message = "Duration must be positive")
        Integer durationInDays,

        @Schema(example = "15000.00")
        @DecimalMin(value = "0.00", message = "Price must be non-negative")
        BigDecimal pricePerPerson
) {}
