package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record CompanyPatchRequest(
        @Schema(example = "GLBL")
        @Size(max = 4, message = "Company code must be at most 4 characters")
        String companyCode,

        @Schema(example = "Global ERP")
        String companyName
) {}
