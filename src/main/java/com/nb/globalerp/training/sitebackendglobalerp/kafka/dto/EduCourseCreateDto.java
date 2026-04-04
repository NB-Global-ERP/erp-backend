package com.nb.globalerp.training.sitebackendglobalerp.kafka.dto;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@JacksonXmlRootElement(localName = "Edu_Course")
public record EduCourseCreateDto(
    @XmlElement(name = "id")
    int id,

    @XmlElement(name = "sCode")
    String code,

    @XmlElement(name = "sCourseHL")
    String course,

    @XmlElement(name = "sDescription")
    String description,

    @XmlElement(name = "nDurationInDays")
    int durationInDays,

    @XmlElement(name = "nPricePerPerson")
    float pricePerPerson
) {}
