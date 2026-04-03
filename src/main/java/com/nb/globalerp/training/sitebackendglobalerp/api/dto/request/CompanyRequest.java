package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CompanyRequest(
        @NotBlank(message = "Company code is required")
        @Size(max = 4, message = "Company code must be at most 4 characters")
        String companyCode,

        @NotBlank(message = "Company name is required")
        String companyName
) {}
