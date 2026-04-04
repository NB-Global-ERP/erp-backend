package com.nb.globalerp.training.sitebackendglobalerp.exception;

public record ApiError(
        int status,
        String error,
        String message
) {}