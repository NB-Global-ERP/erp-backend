package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CompanyPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CompanyRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CompanyResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.CompanyMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyResponse findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int create(CompanyRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public CompanyResponse update(int id, CompanyPatchRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void delete(int id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
