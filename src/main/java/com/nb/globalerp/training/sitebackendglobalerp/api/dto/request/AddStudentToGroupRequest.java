package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record AddStudentToGroupRequest(
    List<StudentAdditional> studentAdditionals
) {
    public record StudentAdditional(
        @Schema(example = "1")
        @NotNull
        @Positive
        int studentId,

        @Schema(example = "1")
        @NotNull
        @Positive
        int groupId,

        @Schema(example = "0")
        @Positive
        float initialProgress
    ) {}
}
