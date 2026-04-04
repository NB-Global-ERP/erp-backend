package com.nb.globalerp.training.sitebackendglobalerp.config.kafka.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;

@ConfigurationProperties(prefix = "entity-factory.kafka")
public record CrackHashKafkaProperties(CrackHashConsumer consumer) {

    public record CrackHashConsumer(KafkaProperties.Consumer config, String topic) {}
}
