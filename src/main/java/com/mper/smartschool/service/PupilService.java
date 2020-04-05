package com.mper.smartschool.service;

import com.mper.smartschool.dto.PupilDto;

import java.util.Collection;

public interface PupilService {

    PupilDto create(PupilDto pupilDto);

    PupilDto update(PupilDto pupilDto);

    Collection<PupilDto> findAll();

    PupilDto findById(Long id);

    void deleteById(Long id);

    Long getCount();
}
