package com.nb.globalerp.training.sitebackendglobalerp.model.repo.student;

import com.nb.globalerp.training.sitebackendglobalerp.model.data.student.StudentData;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentData, Integer> {
    @Transactional
    Optional<StudentData> findByName(String name);

    @Transactional
    Optional<StudentData> findByStudentDataEmail(String email);

    @Query("SELECT user from UserData user WHERE user.details.accountLocked = FALSE")
    List<StudentData> findAllAlive(Sort sort);

    @Query("SELECT user from UserData user WHERE user.details.accountLocked = FALSE "
            + "AND user.studentData.faculty = :faculty")
    List<StudentData> findAllAliveByFaculty(Faculty faculty, Sort sort);

    @Query("SELECT userTask FROM UserData user "
            + "JOIN UserTasksData userTask ON userTask.userToTask.userData.id = user.id "
            + "WHERE user.id = :userId AND userTask.userToTask.taskData.id = :taskId")
    List<StudentData> findUserTaskByUserAndTask(Integer userId, Integer taskId);
}
