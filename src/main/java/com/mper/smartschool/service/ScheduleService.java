package com.mper.smartschool.service;

import com.mper.smartschool.dto.ScheduleDto;

import java.util.Collection;

public interface ScheduleService {

    ScheduleDto create(ScheduleDto scheduleDto);

    ScheduleDto update(ScheduleDto scheduleDto);

    Collection<ScheduleDto> findAll();

    ScheduleDto findById(Long id);

    void deleteById(Long id);
}
