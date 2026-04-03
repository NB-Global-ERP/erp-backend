package com.nb.globalerp.training.sitebackendglobalerp.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class GroupResponse {
    private int id;
    private Integer courseId;
    private Instant dateBegin;
    private Instant dateEnd;
    private BigDecimal pricePerPerson;
    private int participantCount;
    private BigDecimal groupPrice;
    private String courseCompletion;
    private float averageProgress;
    private Integer specificationId;
}
