package com.nb.globalerp.training.sitebackendglobalerp.kafka.consumer;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.nb.globalerp.training.sitebackendglobalerp.kafka.dto.EduParticipantCreateDto;
import com.nb.globalerp.training.sitebackendglobalerp.services.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EduParticipantXmlConsumer {

    private final StudentService studentService;

    private final XmlMapper xmlMapper = new XmlMapper();

    @KafkaListener(
        containerFactory = "crackHashTaskResultKafkaListenerContainerFactory",
        topics = "edu_participant_factory_xml_consumer_topic",
        groupId = "edu_participant_factory_xml_consumer_group"
    )
    public void consumeNewParticipantFromXml(ConsumerRecord<String, String> consumerRecord) {
        String xmlValue = consumerRecord.value();
        log.info("Consuming edu_participant record: topic={}, partition={}, offset={}", consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset());
        try {
            EduParticipantCreateDto eduParticipantCreateDto = xmlMapper.readValue(xmlValue, EduParticipantCreateDto.class);
            studentService.createFromExternalSystem(eduParticipantCreateDto);
            log.info("Successfully processed edu_participant record: offset={}", consumerRecord.offset());
        } catch (Exception e) {
            log.error("Failed to process edu_participant record: offset={}, error={}", consumerRecord.offset(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
