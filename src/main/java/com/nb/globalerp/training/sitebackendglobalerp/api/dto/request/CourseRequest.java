package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CourseRequest(
        @NotBlank(message = "Course name is required")
        String name,

        @NotBlank(message = "Course description is required")
        String description,

        @Positive(message = "Duration must be positive")
        int durationInDays,

        @NotNull(message = "Price per person is required")
        @DecimalMin(value = "0.00", message = "Price must be non-negative")
        BigDecimal pricePerPerson
) {}
