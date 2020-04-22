package com.mper.smartschool.service;

import com.mper.smartschool.dto.GenerateScheduleDto;
import com.mper.smartschool.dto.TemplateScheduleDto;

import java.time.LocalDate;

public interface ScheduleGeneratorService {
    //Following methods without tests
    void generateSchedule(GenerateScheduleDto generateScheduleDto);

    Boolean canTeacherHoldLesson(TemplateScheduleDto templateScheduleDto, LocalDate startDate, LocalDate endDate);
}
