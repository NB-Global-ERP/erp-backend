package com.nb.globalerp.training.sitebackendglobalerp.mapper;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.GroupMemberResponse;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.GroupMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GroupMemberMapper {
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "groupId", source = "group.id")
    GroupMemberResponse toGroupMemberResponse(GroupMember groupMember);
}
