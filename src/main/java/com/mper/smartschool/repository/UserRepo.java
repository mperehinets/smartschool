package com.mper.smartschool.repository;

import com.mper.smartschool.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
}
