package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;

public record StudentPatchRequest(
        @Schema(example = "Иван")
        String firstName,

        @Schema(example = "Петрович")
        String middleName,

        @Schema(example = "Иванов")
        String lastName,

        @Schema(example = "1")
        @Positive(message = "Company ID must be positive")
        Integer companyId,

        @Schema(example = "ivanov@example.com")
        @Email(message = "Email must be valid")
        String email
) {}
