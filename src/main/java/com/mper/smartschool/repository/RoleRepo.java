package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    @Query("select r from Role r where r.name = 'ROLE_ADMIN'")
    Role findAdminRole();

    @Query("select r from Role r where r.name = 'ROLE_TEACHER'")
    Role findTeacherRole();

    @Query("select r from Role r where r.name = 'ROLE_PUPIL'")
    Role findPupilRole();

    @Query("select r from Role r where r.name = 'ROLE_USER'")
    Role findUserRole();
}
