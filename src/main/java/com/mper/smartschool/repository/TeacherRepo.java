package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    @Query("select t from Teacher t" +
            " left join SchoolClass s on s.classTeacher.id = t.id" +
            " where s is null and t.status = 'ACTIVE'")
    Collection<Teacher> findFree();

    @Query("select t from Teacher t" +
            " join TeachersSubject ts on ts.teacher = t" +
            " where ts.subject.id = :subjectId and ts.status = 'ACTIVE'")
    Collection<Teacher> findBySubjectId(@Param("subjectId") Long subjectId);
}
