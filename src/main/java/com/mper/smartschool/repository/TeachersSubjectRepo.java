package com.mper.smartschool.repository;

import com.mper.smartschool.entity.TeachersSubject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface TeachersSubjectRepo extends CrudRepository<TeachersSubject, Long> {
    @Transactional
    @Modifying
    @Query("update TeachersSubject set endDate = current_date where id = :id")
    int stopTeachSubjectById(@Param("id") Long id);
}
