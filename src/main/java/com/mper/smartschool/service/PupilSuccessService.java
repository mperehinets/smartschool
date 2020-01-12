package com.mper.smartschool.service;

import com.mper.smartschool.dto.PupilSuccessDto;

import java.util.Collection;

public interface PupilSuccessService {

    PupilSuccessDto create(PupilSuccessDto pupilSuccessDto);

    PupilSuccessDto update(PupilSuccessDto pupilSuccessDto);

    Collection<PupilSuccessDto> findAll();

    PupilSuccessDto findById(Long id);

    void deleteById(Long id);
}
