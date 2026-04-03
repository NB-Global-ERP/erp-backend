package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StudentRequest(
        @Schema(example = "Иван")
        @NotBlank(message = "First name is required")
        String firstName,

        @Schema(example = "Петрович")
        @NotBlank(message = "Middle name is required")
        String middleName,

        @Schema(example = "Иванов")
        @NotBlank(message = "Last name is required")
        String lastName,

        @Schema(example = "1")
        @NotNull(message = "Company ID is required")
        @Positive(message = "Company ID must be positive")
        Integer companyId,

        @Schema(example = "ivanov@example.com")
        @Email(message = "Email must be valid")
        String email
) {}
