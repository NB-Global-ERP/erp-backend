package com.nb.globalerp.training.sitebackendglobalerp.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "groups")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "date_begin")
    private Instant dateBegin;

    @Column(name = "date_end")
    private Instant dateEnd;

    @Column(name = "price_per_person")
    private BigDecimal pricePerPerson;

    @Column(name = "group_price")
    private BigDecimal groupPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_completion_id")
    private CourseCompletionStatus courseCompletionStatus;

    @Column(name = "average_progress")
    private float averageProgress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specification_id")
    private Specification specification;

    @ManyToMany(mappedBy = "groups")
    private List<Student> students;
}
