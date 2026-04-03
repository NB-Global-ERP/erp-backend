package com.nb.globalerp.training.sitebackendglobalerp.api.controller;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.SpecificationPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.SpecificationRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.SpecificationResponse;
import com.nb.globalerp.training.sitebackendglobalerp.services.SpecificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/specifications")
@RestController
public class SpecificationController {

    private final SpecificationService specificationService;

    @GetMapping
    public ResponseEntity<SpecificationResponse> getById(@RequestParam @Positive int id) {
        var response = specificationService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody @Valid SpecificationRequest request) {
        var response = specificationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping
    public ResponseEntity<SpecificationResponse> update(@RequestParam @Positive int id, @RequestBody @Valid SpecificationPatchRequest request) {
        var response = specificationService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam @Positive int id) {
        specificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
