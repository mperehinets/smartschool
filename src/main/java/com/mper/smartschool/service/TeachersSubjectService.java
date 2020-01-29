package com.mper.smartschool.service;

import com.mper.smartschool.dto.TeachersSubjectDto;

import java.util.Collection;

public interface TeachersSubjectService {

    TeachersSubjectDto create(TeachersSubjectDto teachersSubjectDto);

    TeachersSubjectDto update(TeachersSubjectDto teachersSubjectDto);

    Collection<TeachersSubjectDto> findAll();

    TeachersSubjectDto findById(Long id);

    void deleteById(Long id);

    void stopTeachSubjectById(Long id);
}
