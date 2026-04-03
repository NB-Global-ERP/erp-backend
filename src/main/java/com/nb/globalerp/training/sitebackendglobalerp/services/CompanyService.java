package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CompanyPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CompanyRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CompanyResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.CompanyMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Company;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public List<CompanyResponse> findAll() {
        return companyRepository.findAll()
                .stream()
                .map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CompanyResponse findById(int id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id:%d".formatted(id)));

        return companyMapper.toResponse(company);
    }

    public int create(CompanyRequest request) {
       Company company = companyMapper.toEntity(request);
       return companyRepository.save(company).getId();
    }

    public CompanyResponse update(int id, CompanyPatchRequest request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id:%d".formatted(id)));

        if(!request.companyCode().isBlank()){
            company.setCompanyCode(request.companyCode());
        }
        if(!request.companyName().isBlank()){
            company.setCompanyName(request.companyName());
        }

        Company answer = companyRepository.save(company);
        return companyMapper.toResponse(answer);

    }

    public void delete(int id) {
        try{
            companyRepository.deleteById(id);
        } catch (Exception ignored) {

        }
    }

    public List<CompanyResponse> getAll() {
        return companyRepository.findAll()
                .stream()
                .map(companyMapper::toResponse)
                .toList();
    }

    public long count() {
        return companyRepository.count();
    }
}
