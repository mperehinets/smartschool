package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.dto.mapper.ScheduleMapper;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.ScheduleRepo;
import com.mper.smartschool.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepo scheduleRepo;
    private final ScheduleMapper scheduleMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ScheduleDto create(ScheduleDto scheduleDto) {
        ScheduleDto result = scheduleMapper.toDto(scheduleRepo.save(scheduleMapper.toEntity(scheduleDto)));
        log.info("IN create - schedule: {} successfully created", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ScheduleDto update(ScheduleDto scheduleDto) {
        findById(scheduleDto.getId());
        ScheduleDto result = scheduleMapper.toDto(scheduleRepo.save(scheduleMapper.toEntity(scheduleDto)));
        log.info("IN update - schedule: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<ScheduleDto> findAll() {
        Collection<ScheduleDto> result = scheduleRepo.findAll()
                .stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} schedules found", result.size());
        return result;
    }

    @Override
    public ScheduleDto findById(Long id) {
        ScheduleDto result = scheduleMapper.toDto(scheduleRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("ScheduleNotFoundException.byId", id)));
        log.info("IN findById - schedule: {} found by id: {}", result, id);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        findById(id);
        scheduleRepo.deleteById(id);
        log.info("IN deleteById - schedule with id: {} successfully deleted", id);
    }
}
