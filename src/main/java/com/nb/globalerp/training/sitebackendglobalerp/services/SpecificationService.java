package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.SpecificationPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.SpecificationRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.GroupResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.SpecificationResponse;
import com.nb.globalerp.training.sitebackendglobalerp.exception.SpecificationAlreadyExistsException;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.SpecificationMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Company;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Group;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Specification;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CompanyRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.GroupRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.SpecificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SpecificationService {

    private final SpecificationRepository specificationRepository;
    private final SpecificationMapper specificationMapper;
    private final CompanyRepository companyRepository;

    private final GroupRepository groupRepository;
    private final GroupService groupService;

    @Transactional
    public List<SpecificationResponse> findAll() {
        return specificationRepository.findAll()
                .stream()
                .map(specificationMapper::toSpecificationResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SpecificationResponse findById(int id) {
        Specification specification = specificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Specification not found with id: " + id));
        return specificationMapper.toSpecificationResponse(specification);
    }

    @Transactional
    public int create(SpecificationRequest request) {
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + request.companyId()));

        Specification specification = specificationMapper.toSpecificationEntity(request);
        specification.setCompany(company);

        if(specificationRepository.findByNumber(request.number()).isPresent()){
            throw new SpecificationAlreadyExistsException("Спецификация с номером: %d уже создана".formatted(request.number()));
        }
        specification.setVatAmount22Percent(BigDecimal.ZERO);
        specification.setTotalAmountExcludingVat(BigDecimal.ZERO);
        specification.setTotalAmountIncludingVat(BigDecimal.ZERO);

        return specificationRepository.save(specification).getId();
    }

    @Transactional
    public SpecificationResponse update(int id, SpecificationPatchRequest request) {
        Specification specification = specificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Specification not found with id: " + id));

        if (request.companyId() != null) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + request.companyId()));
            specification.setCompany(company);
        }
        if(request.datetime() != null){
            specification.setDatetime(request.datetime());
        }
        if(request.number() != null){
            if(specificationRepository.findByNumber(request.number()).isPresent()){
                throw new SpecificationAlreadyExistsException("Спецификация с номером: %d уже создана".formatted(request.number()));
            }
            specification.setNumber(request.number());
        }
        specificationMapper.updateSpecificationEntity(specification, request);

        return specificationMapper.toSpecificationResponse(specificationRepository.save(specification));
    }
    @Transactional
    public void delete(int id) {
        Specification specification = specificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Specification not found with id: " + id));

        List<Group> groups = groupRepository.findAllBySpecification_Id(id);

        for(Group group : groups){
            groupService.deleteWithOutSpecification(group);
        }
        specificationRepository.deleteById(id);

    }

    public Long count() {
        return specificationRepository.count();
    }
}

