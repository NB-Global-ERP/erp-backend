package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;

public record SpecificationRequest(
        @Schema(example = "2025-08-15T00:00:00Z")
        @NotNull(message = "Date is required")
        Instant datetime,

        @Schema(example = "42")
        @Positive(message = "Number must be positive")
        int number,

        @Schema(example = "1")
        @NotNull(message = "Company ID is required")
        @Positive(message = "Company ID must be positive")
        Integer companyId,

        @Schema(example = "100000.00")
        @NotNull(message = "Total amount excluding VAT is required")
        @DecimalMin(value = "0.00", message = "Amount must be non-negative")
        BigDecimal totalAmountExcludingVat,

        @Schema(example = "22000.00")
        @NotNull(message = "VAT amount is required")
        @DecimalMin(value = "0.00", message = "Amount must be non-negative")
        BigDecimal vatAmount22Percent,

        @Schema(example = "122000.00")
        @NotNull(message = "Total amount including VAT is required")
        @DecimalMin(value = "0.00", message = "Amount must be non-negative")
        BigDecimal totalAmountIncludingVat
) {}
