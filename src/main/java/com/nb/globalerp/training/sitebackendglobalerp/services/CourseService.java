package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CoursePatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.CourseMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseResponse findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int create(CourseRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public CourseResponse update(int id, CoursePatchRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void delete(int id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
