package com.nb.globalerp.training.sitebackendglobalerp.mapper;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.SpecificationPatchRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.SpecificationRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.SpecificationResponse;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Specification;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SpecificationMapper {

    @Mapping(target = "companyId", source = "company.id")
    SpecificationResponse toSpecificationResponse(Specification specification);

    @Mapping(target = "totalAmountExcludingVat", ignore = true)
    @Mapping(target = "vatAmount22Percent", ignore = true)
    @Mapping(target = "totalAmountIncludingVat", ignore = true)
    Specification toSpecificationEntity(SpecificationRequest request);

    void updateSpecificationEntity(@MappingTarget Specification specification, SpecificationPatchRequest request);
}
