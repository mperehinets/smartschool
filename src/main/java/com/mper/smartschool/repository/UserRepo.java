package com.mper.smartschool.repository;

import com.mper.smartschool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("update User set status = 'DELETED' where id = :id")
    int setDeletedStatusById(@Param("id") Long id);

    Optional<User> findByEmail(String email);
}
