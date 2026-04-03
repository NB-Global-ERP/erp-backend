package com.nb.globalerp.training.sitebackendglobalerp.api.dto.response;

import java.math.BigDecimal;

public record CourseResponse(
        int id,
        String name,
        String description,
        int durationInDays,
        BigDecimal pricePerPerson
) {}
