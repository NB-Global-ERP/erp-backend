package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CourseCompletionStatusRequest(
        @Schema(example = "В процессе")
        @NotBlank(message = "Status name is required")
        String name
) {}
