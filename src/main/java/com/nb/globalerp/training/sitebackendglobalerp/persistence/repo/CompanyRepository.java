package com.nb.globalerp.training.sitebackendglobalerp.persistence.repo;

import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query(value = """
        SELECT
            -- 1. Кол-во уникальных сотрудников в обучении
            COUNT(DISTINCT gm.student_id) as total_students_in_training,

            -- 2. % сотрудников, обучающихся сейчас
            COUNT(DISTINCT CASE 
                WHEN cs.name = 'IN_PROCESS' THEN gm.student_id 
            END) * 100.0 / NULLIF((SELECT COUNT(*) FROM students s WHERE s.company_id = :companyId), 0),

            -- 3. % сотрудников, обучавшихся когда-либо
            COUNT(DISTINCT gm.student_id) * 100.0 / NULLIF((SELECT COUNT(*) FROM students s WHERE s.company_id = :companyId), 0),

            -- 4. Средний процент прохождения
            AVG(gm.completion_percent)

        FROM group_members gm
        JOIN groups g ON gm.group_id = g.id
        JOIN students s ON gm.student_id = s.id
        JOIN course_completion_statuses cs ON cs.id = g.course_completion_id
        WHERE s.company_id = :companyId
    """, nativeQuery = true)
    Object[] getTrainingEfficiencyStats(@Param("companyId") Integer companyId);
}
