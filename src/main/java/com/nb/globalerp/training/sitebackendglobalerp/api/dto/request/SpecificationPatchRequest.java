package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;

public record SpecificationPatchRequest(
        Instant date,

        @Positive(message = "Number must be positive")
        Integer number,

        @Positive(message = "Company ID must be positive")
        Integer companyId,

        @DecimalMin(value = "0.00", message = "Amount must be non-negative")
        BigDecimal totalAmountExcludingVat,

        @DecimalMin(value = "0.00", message = "Amount must be non-negative")
        BigDecimal vatAmount22Percent,

        @DecimalMin(value = "0.00", message = "Amount must be non-negative")
        BigDecimal totalAmountIncludingVat
) {}
