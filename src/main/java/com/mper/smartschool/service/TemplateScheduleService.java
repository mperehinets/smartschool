package com.mper.smartschool.service;

import com.mper.smartschool.dto.TemplateScheduleDto;

import java.util.Collection;

public interface TemplateScheduleService {

    TemplateScheduleDto create(TemplateScheduleDto templateScheduleDto);

    TemplateScheduleDto update(TemplateScheduleDto templateScheduleDto);

    Collection<TemplateScheduleDto> findAll();

    TemplateScheduleDto findById(Long id);

    void deleteById(Long id);
}
