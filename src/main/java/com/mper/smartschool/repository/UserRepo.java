package com.mper.smartschool.repository;

import com.mper.smartschool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User set status = 'DELETED' where id = :id")
    void setDeletedStatusById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update User set status = 'ACTIVE' where id = :id")
    void setActiveStatusById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update User set status = 'NOT_ACTIVE' where id = :id")
    void setNotActiveStatusById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update User set password = :password  where id = :id")
    void updatePasswordById(@Param("id") Long id, @Param("password") String password);
}
