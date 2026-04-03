package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseCompletionStatusPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseCompletionStatusRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CoursePatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseCompletionStatusResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.CourseMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Company;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Course;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Student;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public List<CourseResponse> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CourseResponse findById(int id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        return courseMapper.toResponse(course);
    }

    public int create(CourseRequest request) {
        Course course = courseMapper.toEntity(request);
        return courseRepository.save(course).getId();
    }

    public CourseResponse update(int id, CoursePatchRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        if (request.name() != null) course.setName(request.name());
        if (request.description() != null) course.setDescription(request.description());
        if (request.durationInDays() != null) course.setDurationInDays(request.durationInDays());
        if (request.pricePerPerson() != null) course.setPricePerPerson(request.pricePerPerson());

        return courseMapper.toResponse(courseRepository.save(course));
    }

    public void delete(int id) {courseRepository.deleteById(id);
    }

    public long count() {
        return courseRepository.count();
    }
}
