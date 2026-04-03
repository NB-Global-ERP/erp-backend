package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseCompletionStatusPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseCompletionStatusRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CoursePatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseCompletionStatusResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.CourseCompletionStatusMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Course;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.CourseCompletionStatus;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CourseCompletionStatusRepository;
import jakarta.persistence.EntityNotFoundException;
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
        CourseCompletionStatus courseCompletionStatus = courseCompletionStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CourseCompletionStatus not found with id: " + id));
        return courseCompletionStatusMapper.toResponse(courseCompletionStatus);
    }

    public int create(CourseCompletionStatusRequest request) {
        CourseCompletionStatus courseCompletionStatus = courseCompletionStatusMapper.toEntity(request);
        return courseCompletionStatusRepository.save(courseCompletionStatus).getId();
    }

    public CourseCompletionStatusResponse update(int id, CourseCompletionStatusPatchRequest request) {
        CourseCompletionStatus courseCompletionStatus = courseCompletionStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CourseCompletionStatus not found with id: " + id));

        if (request.name() != null) courseCompletionStatus.setName(request.name());

        return courseCompletionStatusMapper.toResponse(courseCompletionStatusRepository.save(courseCompletionStatus));
    }

    public void delete(int id) {
        courseCompletionStatusRepository.deleteById(id);
    }
}
