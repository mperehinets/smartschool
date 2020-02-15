package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
}
