package com.mper.smartschool.repository;

import com.mper.smartschool.entity.SignedPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignedPersonRepo extends JpaRepository<SignedPerson, Long> {
}
