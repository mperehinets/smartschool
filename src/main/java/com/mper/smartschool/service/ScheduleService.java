package com.mper.smartschool.service;

import com.mper.smartschool.dto.GenerateScheduleDto;
import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.dto.TemplateScheduleDto;

import java.time.LocalDate;
import java.util.Collection;

public interface ScheduleService {

    ScheduleDto create(ScheduleDto scheduleDto);

    ScheduleDto update(ScheduleDto scheduleDto);

    Collection<ScheduleDto> findAll();

    ScheduleDto findById(Long id);

    void deleteById(Long id);

    //Following methods without tests
    void generateSchedule(GenerateScheduleDto generateScheduleDto);

    Boolean canTeacherHoldLesson(TemplateScheduleDto templateScheduleDto, LocalDate date);

    LocalDate findMinGenerationDateByClassId(Long classId);
}
