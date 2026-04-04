package com.nb.globalerp.training.sitebackendglobalerp.mapper;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.StudentRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.StudentResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.StudentRiskResponse;
import com.nb.globalerp.training.sitebackendglobalerp.kafka.dto.EduParticipantCreateDto;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Student;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.StudentRiskProjection;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StudentMapper {

    @Mapping(target = "companyId", source = "company.id")
    StudentResponse toStudentResponse(Student student);

    Student toStudentEntity(StudentRequest request);

    @Mapping(target = "id", ignore = true)
    Student toStudentEntity(EduParticipantCreateDto request);

    StudentRiskResponse toStudentRiskResponse(StudentRiskProjection projection);

    List<StudentRiskResponse> toStudentRiskResponseList(List<StudentRiskProjection> projections);
}
