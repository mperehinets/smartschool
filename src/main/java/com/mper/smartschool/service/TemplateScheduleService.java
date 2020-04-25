package com.mper.smartschool.service;

import com.mper.smartschool.dto.TemplateScheduleDto;

import java.util.Collection;

public interface TemplateScheduleService {

    TemplateScheduleDto create(TemplateScheduleDto templateScheduleDto);

    TemplateScheduleDto update(TemplateScheduleDto templateScheduleDto);

    Collection<TemplateScheduleDto> findAll();

    TemplateScheduleDto findById(Long id);

    void deleteById(Long id);

    //Following methods without tests
    Collection<TemplateScheduleDto> findByClassNumber(Integer classNumber);

    Long getCount();

    Collection<TemplateScheduleDto> updateAll(Collection<TemplateScheduleDto> templatesScheduleDto);
}
