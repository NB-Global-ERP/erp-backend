package com.nb.globalerp.training.sitebackendglobalerp.mapper;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.request.CompanyRequest;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CompanyResponse;
import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.CompanyStudentsStatsResponse;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Company;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyMapper {

    CompanyResponse toResponse(Company company);

    @Mapping(target = "id", ignore = true)
    Company toEntity(CompanyRequest request);

    default CompanyStudentsStatsResponse toStudentsStats(Object[] stats) {
        if (stats == null) {
            return emptyStats();
        }

        return CompanyStudentsStatsResponse.builder()
                .totalStudents(toLong(stats[0]))
                .currentPercent(toDouble(stats[1]))
                .historicalPercent(toDouble(stats[2]))
                .avgCompletion(toDouble(stats[3]))
                .build();
    }

    default Long toLong(Object value) {
        return value != null ? ((Number) value).longValue() : 0L;
    }

    default Double toDouble(Object value) {
        return value != null ? ((Number) value).doubleValue() : 0.0;
    }

    default CompanyStudentsStatsResponse emptyStats() {
        return CompanyStudentsStatsResponse.builder()
                .totalStudents(0L)
                .currentPercent(0.0)
                .historicalPercent(0.0)
                .avgCompletion(0.0)
                .build();
    }
}
