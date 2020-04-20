package com.mper.smartschool.service;

import com.mper.smartschool.dto.TeachersSubjectDto;

import java.util.Collection;

public interface TeachersSubjectService {

    TeachersSubjectDto create(TeachersSubjectDto teachersSubjectDto);

    Collection<TeachersSubjectDto> findAll();

    TeachersSubjectDto findById(Long id);

    TeachersSubjectDto delete(Long teacherId, Long subjectId);

    //Following methods without tests
    TeachersSubjectDto findByTeacherIdAndSubjectId(Long teacherId, Long subjectId);

    Integer countByTeacherId(Long teacherId);
}

