package com.nb.globalerp.training.sitebackendglobalerp.model.data.student;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "students")
@NoArgsConstructor
@RequiredArgsConstructor
public class StudentData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer studentId;

    @Column(nullable = false, length = 255)
    @NonNull
    String firstName;

    @Column(nullable = false, length = 255)
    @NonNull
    String middleName;

    @Column(nullable = false, length = 255)
    String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    Integer companyId;

    @Column(nullable = false, unique = true)
    @NonNull
    String email;
}
