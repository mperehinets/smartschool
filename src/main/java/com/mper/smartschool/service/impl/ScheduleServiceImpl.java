package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.GenerateScheduleDto;
import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.dto.mapper.ScheduleMapper;
import com.mper.smartschool.dto.mapper.SchoolClassMapper;
import com.mper.smartschool.dto.mapper.TeachersSubjectMapper;
import com.mper.smartschool.entity.Schedule;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.exception.TeachersIsBusyException;
import com.mper.smartschool.repository.ScheduleRepo;
import com.mper.smartschool.service.ScheduleService;
import com.mper.smartschool.service.SchoolClassService;
import com.mper.smartschool.service.TeachersSubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepo scheduleRepo;
    private final ScheduleMapper scheduleMapper;
    private final TeachersSubjectMapper teachersSubjectMapper;
    private final SchoolClassMapper schoolClassMapper;
    private final SchoolClassService schoolClassService;
    private final TeachersSubjectService teachersSubjectService;

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

    @Override
    public void generateSchedule(GenerateScheduleDto generateScheduleDto) {
        //Check if schoolClass exists
        schoolClassService.findById(generateScheduleDto.getSchoolClass().getId());
        Collection<Schedule> result = new ArrayList<>();
        Collection<TemplateScheduleDto> invalidSchedules = new ArrayList<>();
        //Check if start date is correct
        LocalDate minGenerationDate = this.findMinGenerationDateByClassId(generateScheduleDto.getSchoolClass().getId());
        if (minGenerationDate.isAfter(generateScheduleDto.getStartDate())) {
            generateScheduleDto.setStartDate(minGenerationDate);
        }
        //Loop from start date to end date
        for (LocalDate date = generateScheduleDto.getStartDate();
             date.isBefore(generateScheduleDto.getEndDate().plusDays(1));
             date = date.plusDays(1)) {
            //Check if weekday
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                //Make finalDate for lambda expression
                final LocalDate finalDate = date;
                //Template schedule for current date
                generateScheduleDto.getTemplatesSchedule()
                        .stream()
                        .filter(template -> template.getDayOfWeek() == finalDate.getDayOfWeek())
                        .forEach(template -> {
                            //Check if teachersSubject exists
                            teachersSubjectService.findById(template.getTeachersSubject().getId());
                            //Check if teacher can hold lesson
                            if (this.canTeacherHoldLesson(template, finalDate)) {
                                result.add(Schedule.builder()
                                        .lessonNumber(template.getLessonNumber())
                                        .teachersSubject(teachersSubjectMapper.toEntity(template.getTeachersSubject()))
                                        .schoolClass(schoolClassMapper.toEntity(generateScheduleDto.getSchoolClass()))
                                        .date(finalDate)
                                        .build());
                            } else if (!invalidSchedules.contains(template)) {
                                invalidSchedules.add(template);
                            }
                        });
            }

        }
        //Check if all schedules is valid
        if (invalidSchedules.isEmpty()) {
            scheduleRepo.saveAll(result);
        } else {
            throw new TeachersIsBusyException(invalidSchedules);
        }
        log.info("IN generateSchedule - schedule for class: {} successfully generated, startDate: {}, endDate: {}",
                generateScheduleDto.getSchoolClass(),
                generateScheduleDto.getStartDate(),
                generateScheduleDto.getEndDate());
    }

    @Override
    public Boolean canTeacherHoldLesson(TemplateScheduleDto templateScheduleDto, LocalDate date) {
        Schedule schedule = this.scheduleRepo.findByTeacherIdAndDateAndLessonNumber(
                templateScheduleDto.getTeachersSubject().getTeacher().getId(),
                date,
                templateScheduleDto.getLessonNumber());
        return schedule == null;
    }

    @Override
    public LocalDate findMinGenerationDateByClassId(Long classId) {
        Schedule schedule = scheduleRepo.findTop1BySchoolClassOrderByDateDesc(
                schoolClassMapper.toEntity(schoolClassService.findById(classId)));
        LocalDate currentDate = LocalDate.now();
        LocalDate result;
        if (schedule == null || schedule.getDate().isBefore(currentDate)) {
            result = currentDate;
        } else {
            result = schedule.getDate().plusDays(1);
        }
        log.info("IN findLastScheduleDateByClassId - min date found: {}", result);
        return result;
    }
}
