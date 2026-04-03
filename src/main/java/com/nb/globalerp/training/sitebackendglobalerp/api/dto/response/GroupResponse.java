package com.nb.globalerp.training.sitebackendglobalerp.api.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record GroupResponse(
        int id,
        Integer courseId,
        Instant dateBegin,
        Instant dateEnd,
        BigDecimal pricePerPerson,
        int participantCount,
        BigDecimal groupPrice,
        Integer courseCompletionId,
        float averageProgress,
        Integer specificationId
) {}
