package com.nb.globalerp.training.sitebackendglobalerp.persistence.repo;

import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT SUM(c.durationInDays) FROM Course c")
    Long sumDurationInDays();

    @Query("SELECT AVG(c.durationInDays) FROM Course c")
    Double avgDurationInDays();

    @Query("SELECT MIN(c.durationInDays) FROM Course c")
    Integer minDurationInDays();

    @Query("SELECT MAX(c.durationInDays) FROM Course c")
    Integer maxDurationInDays();
}
