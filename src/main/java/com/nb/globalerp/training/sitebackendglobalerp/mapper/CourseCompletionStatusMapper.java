package com.nb.globalerp.training.sitebackendglobalerp.mapper;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseCompletionStatusRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CourseRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseCompletionStatusResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CourseResponse;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Course;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.CourseCompletionStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseCompletionStatusMapper {
    @Mapping(source = "id", target = "id")
    CourseCompletionStatusResponse toResponse(CourseCompletionStatus courseCompletionStatus);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    CourseCompletionStatus toEntity(CourseCompletionStatusRequest request);
}
