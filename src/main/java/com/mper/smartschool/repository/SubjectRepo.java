package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Subject;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

public interface SubjectRepo extends JpaRepository<Subject, Long> {

    Optional<Subject> findByName(String name);

    Collection<Subject> findByStatus(EntityStatus status);

    @Transactional
    @Modifying
    @Query("update Subject set status = :status where id = :id")
    int changeStatusById(@Param("id") Long id, @Param("status") EntityStatus status);

    @Query("select ts.subject from TeachersSubject ts where ts.teacher.id = :teacherId and ts.status = :status")
    Collection<Subject> findByTeacherIdAndStatus(@Param("teacherId") Long teacherId,
                                                 @Param("status") EntityStatus status);

    @Query("select distinct (s) from Subject s" +
            " join TemplateSchedule ts on ts.subject = s" +
            " where ts.classNumber = :classNumber")
    Collection<Subject> findFromTemplatesScheduleByClassNumber(@Param("classNumber") Integer classNumber);

    @Query(value = "select * from subjects s" +
            " where ?1 >= SUBSTRING_INDEX(s.class_interval, '-', 1)" +
            " and ?1 <= SUBSTRING_INDEX(s.class_interval, '-', -1)" +
            " and s.status = 'ACTIVE'",
            nativeQuery = true)
    Collection<Subject> findByClassNumber(Integer classNumber);
}
