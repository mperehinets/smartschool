package com.mper.smartschool.repository;

import com.mper.smartschool.entity.TemplateSchedule;
import org.springframework.data.repository.CrudRepository;

import java.time.DayOfWeek;

public interface TemplateScheduleRepo extends CrudRepository<TemplateSchedule, Long> {

    int countByClassNumberAndDayOfWeek(Integer classNumber, DayOfWeek dayOfWeek);
}
