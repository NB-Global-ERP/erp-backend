package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CoursePatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.SimpleStatsResponse;
import com.nb.globalerp.training.sitebackendglobalerp.kafka.dto.EduCourseCreateDto;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.CourseMapper;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.SimpleStatsMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Course;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final SimpleStatsMapper simpleStatsMapper;

    public List<CourseResponse> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toCourseResponse)
                .collect(Collectors.toList());
    }

    public CourseResponse findById(int id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        return courseMapper.toCourseResponse(course);
    }

    public int create(CourseRequest request) {
        Course course = courseMapper.toCourseEntity(request);
        return courseRepository.save(course).getId();
    }

    public int createFromExternalSystem(EduCourseCreateDto eduCourseCreateDto) {
        Course course = courseMapper.toCourseEntity(eduCourseCreateDto);
        course.setName(eduCourseCreateDto.course());
        course.setExternalId(eduCourseCreateDto.id());
        course.setExternalCode(eduCourseCreateDto.code());
        return courseRepository.save(course).getId();
    }

    public CourseResponse update(int id, CoursePatchRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        if (request.name() != null) course.setName(request.name());
        if (request.description() != null) course.setDescription(request.description());
        if (request.durationInDays() != null) course.setDurationInDays(request.durationInDays());
        if (request.pricePerPerson() != null) course.setPricePerPerson(request.pricePerPerson());

        return courseMapper.toCourseResponse(courseRepository.save(course));
    }

    public void delete(int id) {courseRepository.deleteById(id);
    }

    public Long getCourseNum() {
        return courseRepository.count();
    }

    public SimpleStatsResponse getBasicStats() {
        List<Object[]> result = courseRepository.getStats();
        if (result.isEmpty()) {
            return simpleStatsMapper.emptyStats();
        }
        return simpleStatsMapper.toSimpleStats(result.getFirst());
    }
}
