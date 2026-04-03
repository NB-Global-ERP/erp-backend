package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CourseRequest(
        @Schema(example = "Java для начинающих")
        @NotBlank(message = "Course name is required")
        String name,

        @Schema(example = "Базовый курс по Java с нуля")
        @NotBlank(message = "Course description is required")
        String description,

        @Schema(example = "30")
        @Positive(message = "Duration must be positive")
        int durationInDays,

        @Schema(example = "15000.00")
        @NotNull(message = "Price per person is required")
        @DecimalMin(value = "0.00", message = "Price must be non-negative")
        BigDecimal pricePerPerson
) {}
