package com.nb.globalerp.training.sitebackendglobalerp.persistence.repo;

import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Query("""
        SELECT
            SUM(g.pricePerPerson),
            AVG(g.pricePerPerson),
            MIN(g.pricePerPerson),
            MAX(g.pricePerPerson)
        FROM Group g
    """)
    List<Object[]> getPricePerPersonStats();

    @Query(value = """
        SELECT
            SUM(EXTRACT(EPOCH FROM (date_end - date_begin))),
            AVG(EXTRACT(EPOCH FROM (date_end - date_begin))),
            MIN(EXTRACT(EPOCH FROM (date_end - date_begin))),
            MAX(EXTRACT(EPOCH FROM (date_end - date_begin)))
        FROM groups
    """, nativeQuery = true)
    List<Object[]> getTimeStats();

    List<Group> findAllByCourse_Id(int courseId);

    List<Group> findAllBySpecification_Id(int specificationId);
}
