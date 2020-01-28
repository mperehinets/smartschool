package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Subject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface SubjectRepo extends CrudRepository<Subject, Long> {
    @Transactional
    @Modifying
    @Query("update Subject set status = 'DELETED' where id = :id")
    int setDeletedStatusById(@Param("id") Long id);
}
