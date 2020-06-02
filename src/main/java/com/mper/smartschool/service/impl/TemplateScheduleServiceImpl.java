package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.dto.mapper.TemplateScheduleMapper;
import com.mper.smartschool.exception.DayFilledByLessonsException;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.TemplateScheduleRepo;
import com.mper.smartschool.service.TemplateScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class TemplateScheduleServiceImpl implements TemplateScheduleService {

    private final TemplateScheduleRepo templateScheduleRepo;
    private final TemplateScheduleMapper templateScheduleMapper;

    @Override
    public TemplateScheduleDto create(TemplateScheduleDto templateScheduleDto) {
        int countLessons = templateScheduleRepo.countByClassNumberAndDayOfWeek(templateScheduleDto.getClassNumber(),
                templateScheduleDto.getDayOfWeek());
        if (countLessons >= 10) {
            throw new DayFilledByLessonsException(templateScheduleDto.getDayOfWeek());
        }

        var result = templateScheduleMapper.toDto(templateScheduleRepo
                .save(templateScheduleMapper.toEntity(templateScheduleDto)));
        log.info("IN create - templateSchedule: {} successfully created", result);
        return result;
    }

    @Override
    public TemplateScheduleDto update(TemplateScheduleDto templateScheduleDto) {
        findById(templateScheduleDto.getId());
        var result = templateScheduleMapper.toDto(templateScheduleRepo
                .save(templateScheduleMapper.toEntity(templateScheduleDto)));
        log.info("IN update - templateSchedule: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<TemplateScheduleDto> findAll() {
        var result = templateScheduleRepo.findAll()
                .stream()
                .map(templateScheduleMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} templateSchedules found", result.size());
        return result;
    }

    @Override
    public TemplateScheduleDto findById(Long id) {
        var result = templateScheduleMapper.toDto(templateScheduleRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("TemplateScheduleNotFoundException.byId", id)));
        log.info("IN findById - templateSchedule: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        templateScheduleRepo.deleteById(id);
        log.info("IN deleteById - templateSchedule with id: {} successfully deleted", id);
    }

    @Override
    public Collection<TemplateScheduleDto> findByClassNumber(Integer classNumber) {
        var result = templateScheduleRepo.findByClassNumber(classNumber)
                .stream()
                .map(templateScheduleMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findByClassNumber - {} templateSchedules found", result.size());
        return result;
    }

    @Override
    public Long getCount() {
        Long result = templateScheduleRepo.count();
        log.info("IN count - count of templates of schedule: {}", result);
        return result;
    }

    @Override
    public Collection<TemplateScheduleDto> updateAll(Collection<TemplateScheduleDto> templatesScheduleDto) {
        templatesScheduleDto.forEach(item -> findById(item.getId()));
        var templatesSchedule = templatesScheduleDto
                .stream()
                .map(templateScheduleMapper::toEntity)
                .collect(Collectors.toList());
        var result = templateScheduleRepo.saveAll(templatesSchedule)
                .stream()
                .map(templateScheduleMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN updateAll - templateSchedule: {} successfully updated", result);
        return result;
    }
}
