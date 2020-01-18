package com.mper.smartschool.repository;

import com.mper.smartschool.entity.SignedPerson;
import org.springframework.data.repository.CrudRepository;

public interface SignedPersonRepo extends CrudRepository<SignedPerson, Long> {
}
