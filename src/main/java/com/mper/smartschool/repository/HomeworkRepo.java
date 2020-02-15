package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkRepo extends JpaRepository<Homework, Long> {
}
