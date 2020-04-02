package com.mper.smartschool.service;

import com.mper.smartschool.dto.ChangeStatusDto;
import com.mper.smartschool.dto.SubjectDto;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;

import java.util.Collection;

public interface SubjectService extends FieldValueExistsService {

    SubjectDto create(SubjectDto subjectDto);

    SubjectDto update(SubjectDto subjectDto);

    Collection<SubjectDto> findAll();

    SubjectDto findById(Long id);

    void changeStatusById(ChangeStatusDto changeStatusDto);

    //Fallowing methods without tests
    SubjectDto findByName(String name);

    Long getCount();

    Collection<SubjectDto> findByStatus(EntityStatus status);

    Collection<SubjectDto> findByTeacherId(Long teacherId);
}
