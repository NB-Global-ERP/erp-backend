package com.nb.globalerp.training.sitebackendglobalerp.api.controller;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CompanyPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CompanyRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CompanyResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CreateResponse;
import com.nb.globalerp.training.sitebackendglobalerp.services.CompanyService;
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

import java.util.List;

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAll(){
        return new ResponseEntity<>(companyService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreateResponse> create(@RequestBody @Valid CompanyRequest request) {
        return new ResponseEntity<>(new CreateResponse(companyService.create(request)), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<CompanyResponse> update(@RequestParam @Positive Integer id, @RequestBody @Valid CompanyPatchRequest request) {
        return new ResponseEntity<>(companyService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam @Positive Integer id) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
