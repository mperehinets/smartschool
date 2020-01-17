package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.dto.mapper.TemplateScheduleMapper;
import com.mper.smartschool.model.TemplateSchedule;
import com.mper.smartschool.repository.TemplateScheduleRepo;
import com.mper.smartschool.service.TemplateScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TemplateScheduleServiceImpl implements TemplateScheduleService {

    private final TemplateScheduleRepo templateScheduleRepo;
    private final TemplateScheduleMapper templateScheduleMapper;

    public TemplateScheduleServiceImpl(TemplateScheduleRepo templateScheduleRepo,
                                       TemplateScheduleMapper templateScheduleMapper) {
        this.templateScheduleRepo = templateScheduleRepo;
        this.templateScheduleMapper = templateScheduleMapper;
    }

    @Override
    public TemplateScheduleDto create(TemplateScheduleDto templateScheduleDto) {
        TemplateScheduleDto result = templateScheduleMapper.toDto(templateScheduleRepo
                .save(templateScheduleMapper.toEntity(templateScheduleDto)));
        log.info("IN create - templateSchedule: {} successfully created", result);
        return result;
    }

    @Override
    public TemplateScheduleDto update(TemplateScheduleDto templateScheduleDto) {
        findById(templateScheduleDto.getId());
        TemplateScheduleDto result = templateScheduleMapper.toDto(templateScheduleRepo
                .save(templateScheduleMapper.toEntity(templateScheduleDto)));
        log.info("IN update - templateSchedule: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<TemplateScheduleDto> findAll() {
        Collection<TemplateScheduleDto> result = ((Collection<TemplateSchedule>) templateScheduleRepo.findAll())
                .stream()
                .map(templateScheduleMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} templateSchedules found", result.size());
        return result;
    }

    @Override
    public TemplateScheduleDto findById(Long id) {
        TemplateScheduleDto result = templateScheduleMapper.toDto(templateScheduleRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TemplateSchedule not found by id: " + id)));
        log.info("IN findById - templateSchedule: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        templateScheduleRepo.deleteById(id);
        log.info("IN deleteById - templateSchedule with id: {} successfully deleted", id);
    }
}
