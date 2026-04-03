package com.nb.globalerp.training.sitebackendglobalerp.api.dto.response;

import java.math.BigDecimal;

public record GroupMemberResponse(
        int id,
        Integer studentId,
        Integer groupId,
        BigDecimal completionPercent
) {}
