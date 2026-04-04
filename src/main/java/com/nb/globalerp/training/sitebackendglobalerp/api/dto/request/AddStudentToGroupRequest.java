package com.nb.globalerp.training.sitebackendglobalerp.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record AddStudentToGroupRequest(
    List<StudentAdditional> studentAdditionals
) {
    public record StudentAdditional(
        @NotNull
        @Positive
        int studentId,

        @NotNull
        @Positive
        int groupId,

        @Positive
        float initialProgress
    ) {}
}
