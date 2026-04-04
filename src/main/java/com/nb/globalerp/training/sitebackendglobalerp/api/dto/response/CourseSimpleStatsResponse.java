package com.nb.globalerp.training.sitebackendglobalerp.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseSimpleStatsResponse {
    Long count;
    Long sum;
    Double avg;
    Integer min;
    Integer max;
}
