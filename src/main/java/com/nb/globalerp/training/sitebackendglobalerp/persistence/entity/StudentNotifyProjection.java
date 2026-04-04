package com.nb.globalerp.training.sitebackendglobalerp.persistence.entity;

import java.time.LocalDateTime;

public interface StudentNotifyProjection {
    Integer getStudentId();
    String getEmail();
    String getFirstName();
    String getLastName();
    Integer getGroupId();
    LocalDateTime getDateEnd();
    Double getCompletionPercent();
}
