package com.nb.globalerp.training.sitebackendglobalerp.mapper;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.StudentRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.StudentResponse;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Student;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StudentMapper {

    StudentResponse toStudentResponse(Student student);

    Student toStudentEntity(StudentRequest request);
}
