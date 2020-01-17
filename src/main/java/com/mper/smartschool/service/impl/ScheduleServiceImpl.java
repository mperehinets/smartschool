package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.dto.mapper.ScheduleMapper;
import com.mper.smartschool.model.Schedule;
import com.mper.smartschool.repository.ScheduleRepo;
import com.mper.smartschool.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepo scheduleRepo;
    private final ScheduleMapper scheduleMapper;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepo scheduleRepo, ScheduleMapper scheduleMapper) {
        this.scheduleRepo = scheduleRepo;
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public ScheduleDto create(ScheduleDto scheduleDto) {
        ScheduleDto result = scheduleMapper.toDto(scheduleRepo.save(scheduleMapper.toEntity(scheduleDto)));
        log.info("IN create - schedule: {} successfully created", result);
        return result;
    }

    @Override
    public ScheduleDto update(ScheduleDto scheduleDto) {
        findById(scheduleDto.getId());
        ScheduleDto result = scheduleMapper.toDto(scheduleRepo.save(scheduleMapper.toEntity(scheduleDto)));
        log.info("IN update - schedule: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<ScheduleDto> findAll() {
        Collection<ScheduleDto> result = ((Collection<Schedule>) scheduleRepo.findAll())
                .stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} schedules found", result.size());
        return result;
    }

    @Override
    public ScheduleDto findById(Long id) {
        ScheduleDto result = scheduleMapper.toDto(scheduleRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found by id: " + id)));
        log.info("IN findById - schedule: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        scheduleRepo.deleteById(id);
        log.info("IN deleteById - schedule with id: {} successfully deleted", id);
    }
}
