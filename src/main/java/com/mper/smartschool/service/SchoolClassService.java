package com.mper.smartschool.service;

import com.mper.smartschool.dto.SchoolClassDto;

import java.util.Collection;

public interface SchoolClassService {

    SchoolClassDto create(SchoolClassDto schoolClassDto);

    SchoolClassDto update(SchoolClassDto schoolClassDto);

    Collection<SchoolClassDto> findAll();

    SchoolClassDto findById(Long id);

    void deleteById(Long id);
}
