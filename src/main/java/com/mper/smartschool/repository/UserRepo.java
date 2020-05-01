package com.mper.smartschool.repository;

import com.mper.smartschool.entity.User;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
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
    @Query("update User set status = :status where id = :id")
    void changeStatusById(@Param("id") Long id, @Param("status") EntityStatus status);

    @Transactional
    @Modifying
    @Query("update User set password = :password  where email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    @Transactional
    @Modifying
    @Query("update User set avatarName = :avatarName  where id = :id")
    void updateAvatarNameById(@Param("id") Long id, @Param("avatarName") String avatarName);
}
