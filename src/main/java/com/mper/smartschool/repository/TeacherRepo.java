package com.mper.smartschool.repository;

import com.mper.smartschool.model.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepo extends CrudRepository<Teacher, Long> {
}
