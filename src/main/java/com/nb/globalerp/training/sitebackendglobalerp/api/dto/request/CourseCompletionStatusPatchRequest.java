package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CourseCompletionStatusPatchRequest(
        @Schema(example = "Завершён")
        String name
) {}
