package com.nb.globalerp.training.sitebackendglobalerp.api.dto.response;

public record StudentResponse(
        int id,
        String firstName,
        String middleName,
        String lastName,
        Integer companyId,
        String email
) {}
