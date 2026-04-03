package com.nb.globalerp.training.sitebackendglobalerp.mapper;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.SpecificationPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.SpecificationRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.SpecificationResponse;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Specification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SpecificationMapper {

    SpecificationResponse toSpecificationResponse(Specification specification);

    Specification toSpecificationEntity(SpecificationRequest request);

    void updateSpecificationEntity(@MappingTarget Specification specification, SpecificationPatchRequest request);
}
