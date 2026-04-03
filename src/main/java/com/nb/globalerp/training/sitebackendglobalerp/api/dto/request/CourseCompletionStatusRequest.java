package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CourseCompletionStatusRequest(
        @NotBlank(message = "Status name is required")
        String name
) {}
