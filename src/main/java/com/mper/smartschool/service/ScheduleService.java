package com.mper.smartschool.service;

import com.mper.smartschool.dto.ScheduleDto;

import java.time.LocalDate;
import java.util.Collection;

public interface ScheduleService {

    ScheduleDto create(ScheduleDto scheduleDto);

    ScheduleDto update(ScheduleDto scheduleDto);

    Collection<ScheduleDto> findAll();

    ScheduleDto findById(Long id);

    void deleteById(Long id);

    //Following methods without tests

    ScheduleDto findLastByClassId(Long classId);

    Collection<ScheduleDto> updateAll(Collection<ScheduleDto> schedulesDto);

    Collection<ScheduleDto> findByClassIdAndDate(Long classId, LocalDate date);

    Boolean canTeacherHoldLesson(Long teacherId, LocalDate date, Integer lessonNumber);
}
