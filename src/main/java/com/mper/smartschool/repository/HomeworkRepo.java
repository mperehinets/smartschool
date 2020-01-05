package com.mper.smartschool.repository;

import com.mper.smartschool.model.Homework;
import org.springframework.data.repository.CrudRepository;

public interface HomeworkRepo extends CrudRepository<Homework, Long> {
}
