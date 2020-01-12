package com.mper.smartschool.service;

import com.mper.smartschool.dto.HomeworkDto;

import java.util.Collection;

public interface HomeworkService {

    HomeworkDto create(HomeworkDto homeworkDto);

    HomeworkDto update(HomeworkDto homeworkDto);

    Collection<HomeworkDto> findAll();

    HomeworkDto findById(Long id);

    void deleteById(Long id);
}
