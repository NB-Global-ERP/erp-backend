package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.*;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.GroupMapper;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.SimpleStatsMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Course;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.CourseCompletionStatus;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Group;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.GroupMember;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Specification;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CourseCompletionStatusRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.CourseRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.GroupMemberRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.GroupRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.SpecificationRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Student;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.*;
import com.nb.globalerp.training.sitebackendglobalerp.utils.WorkCalendarService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupService {

    private final CourseCompletionStatusRepository courseCompletionStatusRepository;
    private final CourseRepository courseRepository;
    private final SpecificationRepository specificationRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    private final GroupMapper groupMapper;
    private final SimpleStatsMapper simpleStatsMapper;
    private final StudentRepository studentRepository;

    @Transactional
    public ListGroupResponse findAll() {
        List<Group> groups =  groupRepository.findAll();

        List<GroupResponse> response = groups
                .stream()
                .map(group -> {
                    GroupResponse dto = groupMapper.toGroupResponse(group);
                    long participantCount = groupMemberRepository.countByGroupId(dto.getId());
                    dto.setParticipantCount(participantCount);
                    return dto;
                })
                .toList();

        List<IdPair> pairs = new ArrayList<>();

        Map<Course, List<Group>> byCourse = groups.stream()
                .collect(Collectors.groupingBy(Group::getCourse));

        for(List<Group> courseGroups : byCourse.values()){

            for (int i = 0; i < courseGroups.size(); i++) {
                for (int j = i + 1; j < courseGroups.size(); j++) {

                    Group g1 = courseGroups.get(i);
                    Group g2 = courseGroups.get(j);

                    if (isOverlap(g1, g2)) {
                        pairs.add(new IdPair(g1.getId(), g2.getId()));
                    }
                }
            }
        }

        ListGroupResponse answer = new ListGroupResponse(response, pairs);

        return answer;
    }

    @Transactional
    public GroupResponse findById(int id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));

        long participantCount = groupMemberRepository.countByGroupId(id);

        GroupResponse groupResponse = groupMapper.toGroupResponse(group);
        groupResponse.setParticipantCount(participantCount);


        return groupResponse;
    }

    @Transactional
    public int create(GroupRequest request) {
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + request.courseId()));

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
        group.setGroupPrice(BigDecimal.ZERO);
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

        long participantCount = groupMemberRepository.countByGroupId(id);

        GroupResponse groupResponse = groupMapper.toGroupResponse(groupRepository.save(group));
        groupResponse.setParticipantCount(participantCount);


        return groupResponse;
    }

    @Transactional
    public DataResponse check(int id, Instant dataBegin){
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));
        int size = group.getCourse().getDurationInDays();

        return new DataResponse(WorkCalendarService.calculateEndDate(dataBegin, size));

    }

    @Transactional
    public void delete(int id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));

        Specification specification = group.getSpecification();
        BigDecimal totalAmountExcludingVat = specification.getTotalAmountExcludingVat();

        totalAmountExcludingVat = totalAmountExcludingVat.subtract(BigDecimal.valueOf(groupMemberRepository.countByGroupId(id)).multiply(group.getPricePerPerson()));

        setAll(totalAmountExcludingVat, specification);

        List<GroupMember> groupMembers = groupMemberRepository.findGroupMemberByGroupId(id);
        groupMemberRepository.deleteAll(groupMembers);

        groupRepository.deleteById(id);
    }

    @Transactional
    public void deleteWithOutSpecification(Group group){
        List<GroupMember> groupMembers = groupMemberRepository.findGroupMemberByGroupId(group.getId());
        groupMemberRepository.deleteAll(groupMembers);

        groupRepository.delete(group);
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

    private void countAverageProgressAndPrice(Integer groupId){
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));

        float averageProgress = groupMemberRepository.sumPercentByGroupId(groupId)
                / groupMemberRepository.countByGroupId(groupId);

        group.setAverageProgress(averageProgress);
        BigDecimal newGroupPrice = group.getGroupPrice().add(group.getPricePerPerson());
        group.setGroupPrice(newGroupPrice);

        groupRepository.save(group);

    }

    private void setAll(BigDecimal totalAmountExcludingVat, Specification specification){
        specification.setTotalAmountExcludingVat(totalAmountExcludingVat);

        BigDecimal percent = new BigDecimal("22");
        specification.setVatAmount22Percent(totalAmountExcludingVat
                .multiply(percent)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP));

        specification.setTotalAmountIncludingVat(specification.getTotalAmountExcludingVat()
                .subtract(specification.getVatAmount22Percent()));

    }

    private boolean isOverlap(Group g1, Group g2) {
        return !g1.getDateBegin().isAfter(g2.getDateEnd()) &&
                !g2.getDateBegin().isAfter(g1.getDateEnd());
    }



}
