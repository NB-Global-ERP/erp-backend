package com.nb.globalerp.training.sitebackendglobalerp.api.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record SpecificationResponse(
        int id,
        Instant datetime,
        int number,
        Integer companyId,
        BigDecimal totalAmountExcludingVat,
        BigDecimal vatAmount22Percent,
        BigDecimal totalAmountIncludingVat
) {}
