package com.nb.globalerp.training.sitebackendglobalerp.services;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.GroupResponse;
import com.nb.globalerp.training.sitebackendglobalerp.mapper.GroupMapper;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.repo.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupResponse findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int create(GroupRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public GroupResponse update(int id, GroupPatchRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void delete(int id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
