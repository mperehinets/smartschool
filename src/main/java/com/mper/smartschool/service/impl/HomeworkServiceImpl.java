package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.HomeworkDto;
import com.mper.smartschool.dto.mapper.HomeworkMapper;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.model.Homework;
import com.mper.smartschool.repository.HomeworkRepo;
import com.mper.smartschool.service.HomeworkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepo homeworkRepo;
    private final HomeworkMapper homeworkMapper;

    @Autowired
    public HomeworkServiceImpl(HomeworkRepo homeworkRepo, HomeworkMapper homeworkMapper) {
        this.homeworkRepo = homeworkRepo;
        this.homeworkMapper = homeworkMapper;
    }

    @Override
    public HomeworkDto create(HomeworkDto homeworkDto) {
        HomeworkDto result = homeworkMapper.toDto(homeworkRepo.save(homeworkMapper.toEntity(homeworkDto)));
        log.info("IN create - homework: {} successfully created", result);
        return result;
    }

    @Override
    public HomeworkDto update(HomeworkDto homeworkDto) {
        HomeworkDto result = homeworkMapper.toDto(homeworkRepo.save(homeworkMapper.toEntity(homeworkDto)));
        log.info("IN update - homework: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<HomeworkDto> findAll() {
        Collection<HomeworkDto> result = ((Collection<Homework>) homeworkRepo.findAll())
                .stream()
                .map(homeworkMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} homeworks found", result.size());
        return result;
    }

    @Override
    public HomeworkDto findById(Long id) {
        HomeworkDto result = homeworkMapper.toDto(homeworkRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Homework not found by id: " + id)));
        log.info("IN findById - homework: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        homeworkRepo.deleteById(id);
        log.info("IN deleteById - homework with id: {} successfully deleted", id);
    }
}
