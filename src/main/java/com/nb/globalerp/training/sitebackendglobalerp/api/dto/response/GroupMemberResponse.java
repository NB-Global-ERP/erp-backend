package com.nb.globalerp.training.sitebackendglobalerp.api.dto.response;

public record GroupMemberResponse(
        int id,
        Integer studentId,
        Integer groupId,
        Float completionPercent
) {}
