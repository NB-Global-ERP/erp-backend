package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.SpecificationPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.SpecificationRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.SpecificationResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.SpecificationMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.SpecificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SpecificationService {

    private final SpecificationRepository specificationRepository;
    private final SpecificationMapper specificationMapper;

    public SpecificationResponse findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int create(SpecificationRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public SpecificationResponse update(int id, SpecificationPatchRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void delete(int id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
