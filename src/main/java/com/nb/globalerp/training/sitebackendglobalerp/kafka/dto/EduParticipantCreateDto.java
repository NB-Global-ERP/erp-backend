package com.nb.globalerp.training.sitebackendglobalerp.kafka.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Edu_Participant")
public record EduParticipantCreateDto(
    @JsonProperty("id")
    int id,

    @JsonProperty("sCode")
    String code,

    @JsonProperty("sLastName")
    String lastName,

    @JsonProperty("sMiddleName")
    String middleName,

    @JsonProperty("sFirstName")
    String firstName,

    @JsonProperty("sFIO")
    String fio,

    @JsonProperty("idOrganization")
    String companyCode,

    @JsonProperty("idOrganizationHL")
    String companyName
) {}
