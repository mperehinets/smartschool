package com.mper.smartschool.repository;

import com.mper.smartschool.entity.TemplateSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.Collection;

public interface TemplateScheduleRepo extends JpaRepository<TemplateSchedule, Long> {

    Integer countByClassNumberAndDayOfWeek(Integer classNumber, DayOfWeek dayOfWeek);

    Collection<TemplateSchedule> findByClassNumber(Integer classNumber);
}
