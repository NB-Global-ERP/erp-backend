package com.nb.globalerp.training.sitebackendglobalerp.api.controller;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CreateCertificateResponse;
import com.nb.globalerp.training.sitebackendglobalerp.services.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/certificates")
@RestController
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping
    public ResponseEntity<byte[]> getCertificateForStudent(
        @RequestParam(required = false) Integer studentId,
        @RequestParam(required = false) Integer groupId,
        @RequestParam(required = false) String certificateId
    ) {
        var response = certificateService.getCertificate(studentId, groupId, certificateId);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=certificate.pdf")
            .header("length", String.valueOf(response.length()))
            .contentType(MediaType.APPLICATION_PDF)
            .body(response.pdfCertBytes());
    }

    @PostMapping("/students/{studentId}/groups/{groupId}")
    public ResponseEntity<CreateCertificateResponse> createCertificateForStudent(
        @PathVariable int studentId,
        @PathVariable int groupId
    ) {
        var response = certificateService.createCertificateForStudent(studentId, groupId);
        return ResponseEntity.ok().body(response);
    }
}
