package com.mper.smartschool.service;

import com.mper.smartschool.dto.TeacherDto;

import java.util.Collection;

public interface TeacherService {

    TeacherDto create(TeacherDto teacherDto);

    TeacherDto update(TeacherDto teacherDto);

    Collection<TeacherDto> findAll();

    TeacherDto findById(Long id);

    //Following methods without tests
    Long getCount();
}
