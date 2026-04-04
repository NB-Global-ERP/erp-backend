package com.nb.globalerp.training.sitebackendglobalerp.persistence.entity;

public interface StudentRiskProjection {
    Integer getStudentId();
    String getFirstName();
    String getLastName();
    Integer getGroupId();

    Double getCompletionPercent();
    Double getTimeProgress();

    String getRiskLevel();
}
