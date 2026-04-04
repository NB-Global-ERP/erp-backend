package com.nb.globalerp.training.sitebackendglobalerp.persistence.repo;

import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("""
        SELECT
            SUM(c.durationInDays),
            AVG(c.durationInDays),
            MIN(c.durationInDays),
            MAX(c.durationInDays)
        FROM Course c
    """)
    List<Object[]> getStats();
}
