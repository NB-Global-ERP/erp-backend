package com.nb.globalerp.training.sitebackendglobalerp.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyStudentsStatsResponse {
    Long totalStudents;
    Double currentPercent;
    Double historicalPercent;
    Double avgCompletion;
}
