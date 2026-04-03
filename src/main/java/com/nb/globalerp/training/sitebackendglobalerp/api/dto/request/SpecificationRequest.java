package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;

public record SpecificationRequest(
        @NotNull(message = "Date is required")
        Instant date,

        @Positive(message = "Number must be positive")
        int number,

        @NotNull(message = "Company ID is required")
        @Positive(message = "Company ID must be positive")
        Integer companyId,

        @NotNull(message = "Total amount excluding VAT is required")
        @DecimalMin(value = "0.00", message = "Amount must be non-negative")
        BigDecimal totalAmountExcludingVat,

        @NotNull(message = "VAT amount is required")
        @DecimalMin(value = "0.00", message = "Amount must be non-negative")
        BigDecimal vatAmount22Percent,

        @NotNull(message = "Total amount including VAT is required")
        @DecimalMin(value = "0.00", message = "Amount must be non-negative")
        BigDecimal totalAmountIncludingVat
) {}
