package com.mper.smartschool.repository;

import com.mper.smartschool.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface SchoolClassRepo extends JpaRepository<SchoolClass, Long> {

    SchoolClass findTop1ByNumberOrderByInitialDesc(Integer number);

    default SchoolClass findLastByNumber(Integer number) {
        return findTop1ByNumberOrderByInitialDesc(number);
    }

    Collection<SchoolClass> findByNumber(Integer number);

    @Query("select c from SchoolClass c where c.classTeacher.id = :teacherId")
    Optional<SchoolClass> findByTeacherId(Long teacherId);
}
