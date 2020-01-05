package com.mper.smartschool.repository;

import com.mper.smartschool.model.SignedPerson;
import org.springframework.data.repository.CrudRepository;

public interface SignedPersonRepo extends CrudRepository<SignedPerson, Long> {
}
