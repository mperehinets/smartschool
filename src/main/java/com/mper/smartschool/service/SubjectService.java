package com.mper.smartschool.service;

import com.mper.smartschool.dto.SubjectDto;

import java.util.Collection;

public interface SubjectService {

    SubjectDto create(SubjectDto subjectDto);

    SubjectDto update(SubjectDto subjectDto);

    Collection<SubjectDto> findAll();

    SubjectDto findById(Long id);

    void deleteById(Long id);
}
