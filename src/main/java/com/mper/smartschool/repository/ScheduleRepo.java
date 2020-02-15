package com.mper.smartschool.repository;

import com.mper.smartschool.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
}
