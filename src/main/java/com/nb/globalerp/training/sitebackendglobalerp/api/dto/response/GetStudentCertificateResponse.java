package com.nb.globalerp.training.sitebackendglobalerp.api.dto.response;

public record GetStudentCertificateResponse(
    String filename,
    long length,
    byte[] pdfCertBytes
) {}
