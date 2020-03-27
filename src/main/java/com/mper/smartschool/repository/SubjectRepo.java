package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Subject;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface SubjectRepo extends JpaRepository<Subject, Long> {
    @Transactional
    @Modifying
    @Query("update Subject set status = :status where id = :id")
    int changeStatusById(@Param("id") Long id, @Param("status") EntityStatus status);
}
