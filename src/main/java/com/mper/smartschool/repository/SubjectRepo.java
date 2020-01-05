package com.mper.smartschool.repository;

import com.mper.smartschool.model.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepo extends CrudRepository<Subject, Long> {
}
