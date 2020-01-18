package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepo extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
