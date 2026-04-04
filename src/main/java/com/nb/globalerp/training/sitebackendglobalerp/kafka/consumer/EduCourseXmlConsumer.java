package com.nb.globalerp.training.sitebackendglobalerp.kafka.consumer;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.nb.globalerp.training.sitebackendglobalerp.kafka.dto.EduCourseCreateDto;
import com.nb.globalerp.training.sitebackendglobalerp.services.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EduCourseXmlConsumer {

    private final CourseService courseService;

    private final XmlMapper xmlMapper = new XmlMapper();

    @KafkaListener(
        containerFactory = "crackHashTaskResultKafkaListenerContainerFactory",
        topics = "edu_course_factory_xml_consumer_topic",
        groupId = "edu_course_factory_xml_consumer_group"
    )
    private void consumeNewEntityFromXml(ConsumerRecords<String, String> consumerRecords) {
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            String xmlValue = consumerRecord.value();
            log.info("Consuming edu_course record: topic={}, partition={}, offset={}", consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset());
            try {
                EduCourseCreateDto eduCourseCreateDto = xmlMapper.readValue(xmlValue, EduCourseCreateDto.class);
                courseService.createFromExternalSystem(eduCourseCreateDto);
                log.info("Successfully processed edu_course record: offset={}", consumerRecord.offset());
            } catch (Exception e) {
                log.error("Failed to process edu_course record: offset={}, error={}", consumerRecord.offset(), e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
    }
}
