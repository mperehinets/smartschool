package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    @Query("select t from Teacher t" +
            " left join SchoolClass s on s.classTeacher.id = t.id" +
            " where s is null and t.status = 'ACTIVE'")
    Collection<Teacher> findFree();
}
