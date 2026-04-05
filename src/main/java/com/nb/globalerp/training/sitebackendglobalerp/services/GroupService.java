package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.AddStudentToGroupRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.DataResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.GroupResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.SimpleStatsResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.GroupMapper;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.SimpleStatsMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Course;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.CourseCompletionStatus;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Group;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.GroupMember;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Specification;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Student;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CourseCompletionStatusRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CourseRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.GroupMemberRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.GroupRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.SpecificationRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.StudentRepository;
import com.nb.globalerp.training.sitebackendglobalerp.utils.WorkCalendarService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
                .map(group -> {
                    GroupResponse dto = groupMapper.toGroupResponse(group);
                    long participantCount = groupMemberRepository.countByGroupId(dto.getId());
                    dto.setParticipantCount(participantCount);
                    dto.setGroupPrice(new BigDecimal(participantCount).multiply(dto.getPricePerPerson()));
                    return dto;
                })
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

        Group group = new Group();
        group.setDateBegin(request.dateBegin());
        group.setDateEnd(WorkCalendarService.calculateEndDate(request.dateBegin(), course.getDurationInDays()));

        group.setCourse(course);
        group.setCourseCompletionStatus(courseCompletionStatus);
        group.setSpecification(specification);

        group.setPricePerPerson(course.getPricePerPerson());

        return groupRepository.save(group).getId();
    }

    @Transactional
    public GroupResponse update(int id, GroupPatchRequest request) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));

        if(request.dateBegin() != null && !Objects.equals(request.dateBegin(), group.getDateBegin())) {
            group.setDateBegin(request.dateBegin());
            group.setDateEnd(WorkCalendarService.calculateEndDate(group.getDateBegin(), group.getCourse().getDurationInDays()));
        }
        if(request.courseCompletionId() != null && !Objects.equals(request.courseCompletionId(), group.getCourseCompletionStatus().getId())){
            group.setCourseCompletionStatus(courseCompletionStatusRepository.findById(request.courseCompletionId())
                    .orElseThrow(() -> new EntityNotFoundException("CourseCompletionId not found with id: " + request.courseCompletionId())));
        }

        if(request.specificationId() != null && !Objects.equals(request.specificationId(), group.getSpecification().getId())){
            group.setSpecification(specificationRepository.findById(request.specificationId())
                    .orElseThrow(() -> new EntityNotFoundException("Specification not found with id: " + request.specificationId())));

        }

        if(request.courseId() != null && !Objects.equals(request.courseId(), group.getCourse().getId())){
            group.setCourse(courseRepository.findById(request.courseId())
                    .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + request.courseId())));

            Specification specification = group.getSpecification();

            BigDecimal studentsCount = (BigDecimal)groupMemberRepository.findGroupMemberByGroupId(id);

            BigDecimal total_amount_excluding_vat = specification.getTotalAmountExcludingVat().subtract(
                    group.getPricePerPerson().multiply(studentsCount));

            total_amount_excluding_vat.add(group.getCourse().getPricePerPerson().multiply(studentsCount));

            BigDecimal percent = new BigDecimal("22");
            BigDecimal vat_amount_22_percent = total_amount_excluding_vat
                    .multiply(percent)
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

            BigDecimal totalAmountIncludingVat = total_amount_excluding_vat.add(vat_amount_22_percent);

            specification.setTotalAmountExcludingVat(total_amount_excluding_vat);
            specification.setVatAmount22Percent(vat_amount_22_percent);
            specification.setTotalAmountIncludingVat(totalAmountIncludingVat);

            specificationRepository.save(specification);

            group.setPricePerPerson(group.getCourse().getPricePerPerson());
        }

        long participantCount = groupMemberRepository.countByGroupId(id);

        GroupResponse groupResponse = groupMapper.toGroupResponse(groupRepository.save(group));
        groupResponse.setParticipantCount(participantCount);
        groupResponse.setGroupPrice(new BigDecimal(participantCount).multiply(group.getPricePerPerson()));

        return groupResponse;
    }

    @Transactional
    public List<Integer> addStudentToGroup(AddStudentToGroupRequest addStudentToGroupRequest) {
        List<Integer> idsMembers = new ArrayList<>();

        for (var studentAdditional : addStudentToGroupRequest.studentAdditionals()) {
            int studentId = studentAdditional.studentId();
            int groupId = studentAdditional.groupId();
            float progress = studentAdditional.initialProgress();

            Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));

            Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

            GroupMember groupMember = GroupMember.builder()
                .student(student)
                .group(group)
                .completionPercent(progress)
                .build();

            GroupMember savedGroupMember = groupMemberRepository.save(groupMember);
            countAverageProgress(groupId);
            idsMembers.add(savedGroupMember.getId());
        }

        return idsMembers;
    }

    public DataResponse check(int id, Instant dataBegin){
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));
        Integer size = group.getCourse().getDurationInDays();

        return new DataResponse(WorkCalendarService.calculateEndDate(dataBegin, size));

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

    private void countAverageProgress(Integer groupId){
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));

        float averageProgress = groupMemberRepository.sumPercentByGroupId(groupId)
                / groupMemberRepository.countByGroupId(groupId);

        group.setAverageProgress(averageProgress);

        groupRepository.save(group);

    }
}
