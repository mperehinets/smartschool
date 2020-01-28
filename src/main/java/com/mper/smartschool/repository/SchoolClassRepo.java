package com.mper.smartschool.repository;

import com.mper.smartschool.entity.SchoolClass;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface SchoolClassRepo extends CrudRepository<SchoolClass, Long> {

    SchoolClass findTop1BySeasonAndNumberOrderByInitialDesc(String season, Integer number);

    @Transactional
    @Modifying
    @Query("update SchoolClass set status = 'DELETED' where id = :id")
    int setDeletedStatusById(@Param("id") Long id);
}
