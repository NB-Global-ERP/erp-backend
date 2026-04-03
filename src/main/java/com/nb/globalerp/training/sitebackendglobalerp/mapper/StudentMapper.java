package com.nb.globalerp.training.sitebackendglobalerp.mapper;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.StudentRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.StudentResponse;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Student;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {

    @Mapping(source = "company.id", target = "companyId")
    StudentResponse toResponse(Student student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    Student toEntity(StudentRequest request);
}
