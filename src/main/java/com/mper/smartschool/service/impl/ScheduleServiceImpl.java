package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.dto.mapper.ScheduleMapper;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.exception.TeacherIsBusyException;
import com.mper.smartschool.repository.ScheduleRepo;
import com.mper.smartschool.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        canTeacherHoldLesson(scheduleDto);
        var result = scheduleMapper.toDto(scheduleRepo.save(scheduleMapper.toEntity(scheduleDto)));
        log.info("IN create - schedule: {} successfully created", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ScheduleDto update(ScheduleDto scheduleDto) {
        findById(scheduleDto.getId());
        canTeacherHoldLesson(scheduleDto);
        var result = scheduleMapper.toDto(scheduleRepo.save(scheduleMapper.toEntity(scheduleDto)));
        log.info("IN update - schedule: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<ScheduleDto> findAll() {
        var result = scheduleRepo.findAll()
                .stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} schedules found", result.size());
        return result;
    }

    @Override
    public ScheduleDto findById(Long id) {
        var result = scheduleMapper.toDto(scheduleRepo.findById(id)
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

    @Override
    public ScheduleDto findLastByClassId(Long classId) {
        var result = scheduleMapper.toDto(scheduleRepo.findLastByClassId(classId));
        log.info("IN findLastByClassId - schedule: {} found", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<ScheduleDto> updateAll(Collection<ScheduleDto> schedulesDto) {
        schedulesDto.forEach(scheduleDto -> {
            var foundScheduleDto = findById(scheduleDto.getId());
            if (!foundScheduleDto.equals(scheduleDto)) {
                canTeacherHoldLesson(scheduleDto);
            }
        });
        var schedules = schedulesDto
                .stream()
                .map(scheduleMapper::toEntity)
                .collect(Collectors.toList());
        var result = scheduleRepo.saveAll(schedules)
                .stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN updateAll - schedules: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<ScheduleDto> findByClassIdAndDate(Long classId, LocalDate date) {
        var result = scheduleRepo.findBySchoolClassIdAndDate(classId, date)
                .stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findByClassIdAndDate - {} schedules found", result.size());
        return result;
    }

    @Override
    public Boolean canTeacherHoldLesson(Long teacherId, LocalDate date, Integer lessonNumber) {
        var schedule = scheduleRepo.findByTeacherIdAndDateAndLessonNumber(teacherId,
                date,
                lessonNumber);
        Boolean result = schedule == null;
        log.info("IN canTeacherHoldLesson - result: {}", result);
        return result;
    }

    private void canTeacherHoldLesson(ScheduleDto scheduleDto) {
        if (!canTeacherHoldLesson(scheduleDto.getTeachersSubject().getTeacher().getId(),
                scheduleDto.getDate(),
                scheduleDto.getLessonNumber())) {
            throw new TeacherIsBusyException(scheduleDto.getTeachersSubject().getTeacher(),
                    scheduleDto.getDate(),
                    scheduleDto.getDate().getDayOfWeek(),
                    scheduleDto.getLessonNumber());
        }
    }
}
