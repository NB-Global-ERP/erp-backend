package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;

public record StudentPatchRequest(
        String firstName,
        String middleName,
        String lastName,

        @Positive(message = "Company ID must be positive")
        Integer companyId,

        @Email(message = "Email must be valid")
        String email
) {}
