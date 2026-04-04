package com.nb.globalerp.training.sitebackendglobalerp.mapper;

import com.nb.globalerp.training.sitebackendglobalerp.api.dto.response.SimpleStatsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SimpleStatsMapper {
    default SimpleStatsResponse toSimpleStats(Object[] stats) {
        if (stats == null) {
            return emptyStats();
        }

        return SimpleStatsResponse.builder()
                .sum(toLong(stats[0]))
                .avg(toDouble(stats[1]))
                .min(toInt(stats[2]))
                .max(toInt(stats[3]))
                .build();
    }

    default Long toLong(Object value) {
        return value != null ? ((Number) value).longValue() : 0L;
    }

    default Double toDouble(Object value) {
        return value != null ? ((Number) value).doubleValue() : 0.0;
    }

    default Integer toInt(Object value) {
        return value != null ? ((Number) value).intValue() : 0;
    }

    default SimpleStatsResponse emptyStats() {
        return SimpleStatsResponse.builder()
                .sum(0L)
                .avg(0.0)
                .min(0)
                .max(0)
                .build();
    }
}
