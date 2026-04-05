package com.nb.globalerp.training.sitebackendglobalerp.persistence.repo;

import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Integer> {
    Optional<Specification> findByNumber(int number);

    List<Specification> findSpecificationByCompany_Id(int companyId);
}
