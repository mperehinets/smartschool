package com.mper.smartschool.service;

import com.mper.smartschool.dto.SchoolClassDto;

import java.util.Collection;

public interface SchoolClassService extends FieldValueExistsService {

    SchoolClassDto create(SchoolClassDto schoolClassDto);

    SchoolClassDto update(SchoolClassDto schoolClassDto);

    Collection<SchoolClassDto> findAll();

    SchoolClassDto findById(Long id);

    void deleteById(Long id);

    //Following method without tests
    Collection<SchoolClassDto> findByNumber(Integer number);

    SchoolClassDto findByTeacherId(Long teacherId);

    Long getCount();
}
