package com.nb.globalerp.training.sitebackendglobalerp.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "specifications")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Specification {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column
    private Instant date;

    @Column
    private int number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "total_amount_excluding_vat")
    private BigDecimal totalAmountExcludingVat;

    @Column(name = "vat_amount_22_percent")
    private BigDecimal vatAmount22Percent;

    @Column(name = "total_amount_including_vat")
    private BigDecimal totalAmountIncludingVat;
}
