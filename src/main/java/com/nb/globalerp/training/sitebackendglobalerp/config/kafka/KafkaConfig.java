package com.nb.globalerp.training.sitebackendglobalerp.config.kafka;

import com.nb.globalerp.training.sitebackendglobalerp.config.kafka.properties.CrackHashKafkaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@ConditionalOnProperty(name = "send-type", havingValue = "kafka")
@RequiredArgsConstructor
@Configuration
public class KafkaConfig {

    private final CrackHashKafkaProperties crackHashKafkaProperties;
    @Bean
    public ConsumerFactory<String, String> crackHashConsumerFactory() {
        var props = crackHashKafkaProperties.consumer().config().buildProperties();
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> crackHashTaskResultKafkaListenerContainerFactory(
        @Qualifier("crackHashConsumerFactory") ConsumerFactory<String, String> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
