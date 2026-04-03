package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CompanyRequest(
        @Schema(example = "GLBL")
        @NotBlank(message = "Company code is required")
        @Size(max = 4, message = "Company code must be at most 4 characters")
        String companyCode,

        @Schema(example = "Global ERP")
        @NotBlank(message = "Company name is required")
        String companyName
) {}
