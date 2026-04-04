package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupMemberPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.GroupMemberResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.GroupMemberMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.GroupMember;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.GroupMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupMemberService {
    private final GroupMemberRepository groupMemberRepository;
    private final GroupMemberMapper mapper;


    public List<GroupMemberResponse> getByGroupId(Integer groupId){
        return groupMemberRepository.findGroupMemberByGroupId(groupId)
                .stream()
                .map(mapper::toGroupMemberResponse)
                .toList();
    }

    @Transactional
    public List<GroupMemberResponse> patchByGroupId(List<GroupMemberPatchRequest> list){
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



        return groupMembers.stream().map(mapper::toGroupMemberResponse).toList();
    }

    public void delete(List<GroupMemberPatchRequest> list){
        List<Integer> ids = list.stream()
                .map(GroupMemberPatchRequest::id)
                .toList();
        groupMemberRepository.deleteAllById(ids);
    }

 }
