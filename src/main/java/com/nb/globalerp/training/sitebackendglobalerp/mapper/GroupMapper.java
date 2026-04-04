package com.nb.globalerp.training.sitebackendglobalerp.mapper;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.GroupRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.GroupResponse;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Group;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GroupMapper {

    Group toGroupEntity(GroupRequest request);


    @Mapping(target = "courseCompletion", source = "courseCompletionStatus.name")
    @Mapping(target = "specificationId", source = "specification.id")
    @Mapping(target = "courseId", source = "course.id")
    GroupResponse toGroupResponse(Group group);

    @Mapping(target = "dateEnd", ignore = true)
    void updateGroupEntity(@MappingTarget Group group, GroupPatchRequest request);
}
