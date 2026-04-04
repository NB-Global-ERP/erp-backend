package com.nb.globalerp.training.sitebackendglobalerp.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "companies")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "external_company_code")
    private String externalCompanyCode;
}
