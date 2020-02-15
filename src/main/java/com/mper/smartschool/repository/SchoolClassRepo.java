package com.mper.smartschool.repository;

import com.mper.smartschool.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface SchoolClassRepo extends JpaRepository<SchoolClass, Long> {

    SchoolClass findTop1BySeasonAndNumberOrderByInitialDesc(String season, Integer number);

    default SchoolClass lastSchoolClassBySeasonAndNumber(String season, Integer number) {
        return findTop1BySeasonAndNumberOrderByInitialDesc(season, number);
    }

    @Transactional
    @Modifying
    @Query("update SchoolClass set status = 'DELETED' where id = :id")
    int setDeletedStatusById(@Param("id") Long id);
}
