package com.nb.globalerp.training.sitebackendglobalerp.api.dto.response;

public record StudentRiskResponse(
        Integer studentId,
        String firstName,
        String lastName,
        Integer groupId,
        Double completionPercent,
        Double timeProgress,
        String riskLevel
) {}
