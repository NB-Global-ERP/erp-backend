package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.Size;

public record CompanyPatchRequest(
        @Size(max = 4, message = "Company code must be at most 4 characters")
        String companyCode,

        String companyName
) {}
