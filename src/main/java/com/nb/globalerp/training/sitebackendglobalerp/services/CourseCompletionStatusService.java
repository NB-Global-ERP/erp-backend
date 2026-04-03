package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseCompletionStatusPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseCompletionStatusRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseCompletionStatusResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.CourseCompletionStatusMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CourseCompletionStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseCompletionStatusService {

    private final CourseCompletionStatusRepository courseCompletionStatusRepository;
    private final CourseCompletionStatusMapper courseCompletionStatusMapper;

    public CourseCompletionStatusResponse findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int create(CourseCompletionStatusRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public CourseCompletionStatusResponse update(int id, CourseCompletionStatusPatchRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void delete(int id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
