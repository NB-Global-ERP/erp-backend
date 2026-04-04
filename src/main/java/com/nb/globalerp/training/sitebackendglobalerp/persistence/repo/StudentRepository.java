package com.nb.globalerp.training.sitebackendglobalerp.persistence.repo;

import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Company;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Student;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.StudentNotifyProjection;
import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.StudentRiskProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByFirstNameAndLastNameAndMiddleNameAndCompany(
            String firstName,
            String lastName,
            String middleName,
            Company company
    );

    @Query(value = """
        SELECT\s
           gm.student_id,
           s.first_name,
           s.last_name,
           g.id as group_id,
           g.date_begin,
           g.date_end,
           gm.completion_percent,
    
           EXTRACT(EPOCH FROM (NOW() - g.date_begin)) /
           EXTRACT(EPOCH FROM (g.date_end - g.date_begin)) AS time_progress,
    
           CASE\s
               WHEN\s
                   (EXTRACT(EPOCH FROM (NOW() - g.date_begin)) /
                    EXTRACT(EPOCH FROM (g.date_end - g.date_begin))) > 0.7
               AND gm.completion_percent < 0.4
               THEN 'HIGH_RISK'
    
               WHEN\s
                   (EXTRACT(EPOCH FROM (NOW() - g.date_begin)) /
                    EXTRACT(EPOCH FROM (g.date_end - g.date_begin))) > 0.5
               AND gm.completion_percent < 0.5
               THEN 'MEDIUM_RISK'
    
               ELSE 'LOW_RISK'
           END AS risk_level
       FROM group_members gm
       JOIN groups g ON gm.group_id = g.id
       JOIN students s ON gm.student_id = s.id;
    """, nativeQuery = true)
    List<StudentRiskProjection> getStudentsRisk();

    @Query(value = """
        SELECT\s
            s.id AS studentId,
            s.email AS email,
            s.first_name AS firstName,
            s.last_name AS lastName,
        
            g.id AS groupId,
            g.date_end AS dateEnd,
        
            gm.completion_percent AS completionPercent
        
        FROM group_members gm
        JOIN students s ON gm.student_id = s.id
        JOIN groups g ON gm.group_id = g.id
        
        WHERE\s
            -- курс ещё не завершён
            g.course_completion_id = 2  -- IN_PROCESS
        
            -- до окончания осталось <= 1 день
            AND g.date_end BETWEEN NOW() AND NOW() + INTERVAL '1 day'
        
            -- студент не завершил курс
            AND gm.completion_percent < 1.0;
    """, nativeQuery = true)
    List<StudentNotifyProjection> getStudentsToNotify();
}
