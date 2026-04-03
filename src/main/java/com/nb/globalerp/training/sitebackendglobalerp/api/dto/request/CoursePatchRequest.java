package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CoursePatchRequest(
        String name,
        String description,

        @Positive(message = "Duration must be positive")
        Integer durationInDays,

        @DecimalMin(value = "0.00", message = "Price must be non-negative")
        BigDecimal pricePerPerson
) {}
