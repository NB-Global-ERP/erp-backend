package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CoursePatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseSimpleStatsResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.CourseMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Course;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public CourseSimpleStatsResponse getBasicStats() {
        List<Object[]> result = courseRepository.getStats();

        if (result.isEmpty()) {
            return CourseSimpleStatsResponse.builder()
                    .count(0L)
                    .sum(0L)
                    .avg(0.0)
                    .min(0)
                    .max(0)
                    .build();
        }

        Object[] stats = result.getFirst();

        return CourseSimpleStatsResponse.builder()
                .count(stats[0] != null ? ((Number) stats[0]).longValue() : 0L)
                .sum(stats[1] != null ? ((Number) stats[1]).longValue() : 0L)
                .avg(stats[2] != null ? ((Number) stats[2]).doubleValue() : 0.0)
                .min(stats[3] != null ? ((Number) stats[3]).intValue() : 0)
                .max(stats[4] != null ? ((Number) stats[4]).intValue() : 0)
                .build();
    }
}
