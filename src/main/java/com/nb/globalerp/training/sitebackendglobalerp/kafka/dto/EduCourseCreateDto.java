package com.nb.globalerp.training.sitebackendglobalerp.kafka.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Edu_Course")
public record EduCourseCreateDto(
    @JsonProperty("id")
    int id,

    @JsonProperty("sCode")
    String code,

    @JsonProperty("sCourseHL")
    String course,

    @JsonProperty("sDescription")
    String description,

    @JsonProperty("nDurationInDays")
    int durationInDays,

    @JsonProperty("nPricePerPerson")
    float pricePerPerson
) {}
