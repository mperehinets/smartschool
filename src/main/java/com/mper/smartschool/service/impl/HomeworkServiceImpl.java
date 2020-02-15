package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.HomeworkDto;
import com.mper.smartschool.dto.mapper.HomeworkMapper;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.HomeworkRepo;
import com.mper.smartschool.service.HomeworkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepo homeworkRepo;
    private final HomeworkMapper homeworkMapper;

    @Override
    @PreAuthorize("hasRole('TEACHER')")
    public HomeworkDto create(HomeworkDto homeworkDto) {
        HomeworkDto result = homeworkMapper.toDto(homeworkRepo.save(homeworkMapper.toEntity(homeworkDto)));
        log.info("IN create - homework: {} successfully created", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('TEACHER')")
    public HomeworkDto update(HomeworkDto homeworkDto) {
        findById(homeworkDto.getId());
        HomeworkDto result = homeworkMapper.toDto(homeworkRepo.save(homeworkMapper.toEntity(homeworkDto)));
        log.info("IN update - homework: {} successfully updated", result);
        return result;
    }


    @Override
    public Collection<HomeworkDto> findAll() {
        Collection<HomeworkDto> result = homeworkRepo.findAll()
                .stream()
                .map(homeworkMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} homeworks found", result.size());
        return result;
    }

    @Override
    public HomeworkDto findById(Long id) {
        HomeworkDto result = homeworkMapper.toDto(homeworkRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("HomeworkNotFoundException.byId", id)));
        log.info("IN findById - homework: {} found by id: {}", result, id);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('TEACHER')")
    public void deleteById(Long id) {
        findById(id);
        homeworkRepo.deleteById(id);
        log.info("IN deleteById - homework with id: {} successfully deleted", id);
    }
}
