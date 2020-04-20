package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Teacher;
import com.mper.smartschool.entity.TeachersSubject;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeachersSubjectRepo extends JpaRepository<TeachersSubject, Long> {

    @Query("select ts from TeachersSubject ts" +
            " where ts.teacher.id = :teacherId and ts.subject.id = :subjectId and ts.status in (:statuses)")
    Optional<TeachersSubject> findByTeacherIdAndSubjectIdAndStatuses(@Param("teacherId") Long teacherId,
                                                                     @Param("subjectId") Long subjectId,
                                                                     @Param("statuses") EntityStatus... statuses);

    Integer countByTeacherAndStatus(Teacher teacher, EntityStatus status);
}
