package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Pupil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PupilRepo extends JpaRepository<Pupil, Long> {
    Integer countBySchoolClassId(Long schoolClassId);
}
