package com.mper.smartschool.repository;

import com.mper.smartschool.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
}
