package com.mper.smartschool.repository;

import com.mper.smartschool.entity.TeachersSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeachersSubjectRepo extends JpaRepository<TeachersSubject, Long> {

    @Query("select ts from TeachersSubject ts where ts.teacher.id = :teacherId and ts.subject.id = :subjectId")
    Optional<TeachersSubject> findByTeacherIdAndSubjectId(@Param("teacherId") Long teacherId,
                                                          @Param("subjectId") Long subjectId);
}
