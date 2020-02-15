package com.mper.smartschool.repository;

import com.mper.smartschool.entity.TemplateSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;

public interface TemplateScheduleRepo extends JpaRepository<TemplateSchedule, Long> {

    int countByClassNumberAndDayOfWeek(int classNumber, DayOfWeek dayOfWeek);
}
