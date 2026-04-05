package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.AddStudentToGroupRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupMemberPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.GroupMemberResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.GroupMemberMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Group;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.GroupMember;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Student;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.GroupMemberRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.GroupRepository;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupMemberService {

    private final StudentRepository studentRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;

    private final GroupMemberMapper mapper;

    public List<GroupMemberResponse> getByGroupId(Integer groupId){
        return groupMemberRepository.findGroupMemberByGroupId(groupId)
                .stream()
                .map(mapper::toGroupMemberResponse)
                .toList();
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

    public List<GroupMemberResponse> patchByGroupId(List<GroupMemberPatchRequest> list, Integer groupId){
        List<Integer> ids = list.stream()
                .map(GroupMemberPatchRequest::id)
                .toList();

        List<GroupMember> groupMembers = groupMemberRepository.findAllById(ids);

        Map<Integer, GroupMemberPatchRequest> requestMap = list.stream()
                .collect(Collectors.toMap(GroupMemberPatchRequest::id, r-> r));


        for (GroupMember groupMember : groupMembers){
            GroupMemberPatchRequest req = requestMap.get(groupMember.getId());

            groupMember.setCompletionPercent(req.completionPercent());
        }

        groupMemberRepository.saveAll(groupMembers);
        countAverageProgress(groupId);


        return groupMembers.stream().map(mapper::toGroupMemberResponse).toList();
    }

    public void delete(List<GroupMemberPatchRequest> list, Integer groupId){
        List<Integer> ids = list.stream()
                .map(GroupMemberPatchRequest::id)
                .toList();
        groupMemberRepository.deleteAllById(ids);

        countAverageProgress(groupId);

    }

    private void countAverageProgress(Integer groupId){
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));

        float averageProgress = groupMemberRepository.sumPercentByGroupId(groupId)
                / groupMemberRepository.countByGroupId(groupId);

        group.setAverageProgress(averageProgress);

        groupRepository.save(group);

    }

 }
