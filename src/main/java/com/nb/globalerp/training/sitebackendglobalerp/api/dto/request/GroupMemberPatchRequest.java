package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record GroupMemberPatchRequest(@Schema(example = "1")
                                @Positive(message = "ID group_member must be positive")
                                Integer id,

                                @Schema(example = "0.75")
                                @DecimalMin(value = "0.00", message = "Completion percent must be at least 0")
                                @DecimalMax(value = "1.00", message = "Completion percent must be at most 1")
                                Float completionPercent) {
}
