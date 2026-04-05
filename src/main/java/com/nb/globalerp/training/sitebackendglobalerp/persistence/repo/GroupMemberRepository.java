package com.nb.globalerp.training.sitebackendglobalerp.persistence.repo;

import com.nb.globalerp.training.sitebackendglobalerp.persistence.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {

    long countByGroupId(int groupId);

    Optional<GroupMember> findByStudentIdAndGroupId(Integer studentId, int groupId);

    List<GroupMember> findGroupMemberByGroupId(int groupId);

    List<GroupMember> findGroupMemberByStudent_Id(Integer studentId);

    @Query("SELECT SUM(gm.completionPercent) FROM GroupMember gm WHERE gm.group.id = :groupId")
    Float sumPercentByGroupId(@Param("groupId") Integer groupId);
}
