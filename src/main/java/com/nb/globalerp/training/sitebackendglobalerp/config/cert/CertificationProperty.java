package com.nb.globalerp.training.sitebackendglobalerp.config.cert;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "student-certificate")
public record CertificationProperty(
    String filenameTemplate,
    Position position
) {
    public record Position(FioPosition fio, CourseDescPosition course) {}

    public record FioPosition(int x, int y) {}
    public record CourseDescPosition(int x, int y) {}
}
