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
@Table(name = "students")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentData {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int studentId;

    @Column
    private String firstName;

    @Column
    private String middleName;

    @Column
    private String lastName;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "company_id")
//    private Integer companyId;

    @Column
    private String email;
}
