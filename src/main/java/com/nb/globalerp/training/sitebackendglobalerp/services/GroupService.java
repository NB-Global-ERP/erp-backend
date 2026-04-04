package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.GroupResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.SimpleStatsResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.GroupMapper;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.SimpleStatsMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Course;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.CourseCompletionStatus;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Group;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Specification;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Student;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CourseCompletionStatusRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CourseRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.GroupMemberRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.GroupRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.SpecificationRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupService {

    private final StudentRepository studentRepository;
    private final CourseCompletionStatusRepository courseCompletionStatusRepository;
    private final CourseRepository courseRepository;
    private final SpecificationRepository specificationRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    private final GroupMapper groupMapper;
    private final SimpleStatsMapper simpleStatsMapper;

    @Transactional
    public List<GroupResponse> findAll() {
        return groupRepository.findAll()
                .stream()
                .map(groupMapper::toGroupResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public GroupResponse findById(int id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));

        long participantCount = groupMemberRepository.countByGroupId(id);

        GroupResponse groupResponse = groupMapper.toGroupResponse(group);
        groupResponse.setParticipantCount(participantCount);
        groupResponse.setGroupPrice(new BigDecimal(participantCount).multiply(group.getPricePerPerson()));
        groupResponse.setCourseCompletion(group.getCourseCompletionStatus().getName());

        return groupResponse;
    }

    @Transactional
    public int create(GroupRequest request) {
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + request.courseId()));

        CourseCompletionStatus courseCompletionStatus = courseCompletionStatusRepository.findById(request.courseCompletionId())
            .orElseThrow(() -> new EntityNotFoundException("CourseCompletionId not found with id: " + request.courseCompletionId()));

        Specification specification = specificationRepository.findById(request.specificationId())
            .orElseThrow(() -> new EntityNotFoundException("Specification not found with id: " + request.specificationId()));

        Group group = groupMapper.toGroupEntity(request);
        group.setCourse(course);
        group.setCourseCompletionStatus(courseCompletionStatus);
        group.setSpecification(specification);

        return groupRepository.save(group).getId();
    }

    @Transactional
    public GroupResponse update(int id, GroupPatchRequest request) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));

        groupMapper.updateGroupEntity(group, request);

        GroupResponse groupResponse = groupMapper.toGroupResponse(groupRepository.save(group));
        groupResponse.setCourseCompletion(group.getCourseCompletionStatus().getName());

        return groupResponse;
    }

    @Transactional
    public void addStudentToGroup(int groupId, int studentId) {
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));

        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        student.getGroups().add(group);
        group.getStudents().add(student);
    }

    public void delete(int id) {
        groupRepository.deleteById(id);
    }

    public Long getGroupNum() {
        return groupRepository.count();
    }

    public SimpleStatsResponse getPricePerPersonStats() {
        List<Object[]> result = groupRepository.getPricePerPersonStats();
        if (result.isEmpty()) {
            return simpleStatsMapper.emptyStats();
        }
        return simpleStatsMapper.toSimpleStats(result.getFirst());
    }

    public SimpleStatsResponse getTimeStats() {
        List<Object[]> result = groupRepository.getTimeStats();
        if (result.isEmpty()) {
            return simpleStatsMapper.emptyStats();
        }
        return simpleStatsMapper.toSimpleStats(result.getFirst());
    }
}
