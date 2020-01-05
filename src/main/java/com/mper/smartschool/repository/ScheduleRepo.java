package com.mper.smartschool.repository;

import com.mper.smartschool.model.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepo extends CrudRepository<Schedule, Long> {
}
