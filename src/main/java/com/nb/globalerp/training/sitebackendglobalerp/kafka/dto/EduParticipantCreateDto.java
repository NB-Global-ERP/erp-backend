package com.nb.globalerp.training.sitebackendglobalerp.kafka.dto;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@JacksonXmlRootElement(localName = "Edu_Participant")
public record EduParticipantCreateDto(
    @XmlElement(name = "id")
    int id,

    @XmlElement(name = "sCode")
    String code,

    @XmlElement(name = "sLastName")
    String lastName,

    @XmlElement(name = "sMiddleName")
    String middleName,

    @XmlElement(name = "sFirstName")
    String firstName,

    @XmlElement(name = "sFIO")
    String fio,

    @XmlElement(name = "idOrganization")
    String companyCode,

    @XmlElement(name = "idOrganizationHL")
    String companyName
) {}
