package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StudentRequest(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Middle name is required")
        String middleName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotNull(message = "Company ID is required")
        @Positive(message = "Company ID must be positive")
        Integer companyId,

        @Email(message = "Email must be valid")
        String email
) {}
