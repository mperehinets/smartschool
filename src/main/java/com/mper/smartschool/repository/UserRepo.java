package com.mper.smartschool.repository;

import com.mper.smartschool.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface UserRepo extends CrudRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("update User set status = 'DELETED' where id = :id")
    int setDeletedStatusById(@Param("id") Long id);
}
