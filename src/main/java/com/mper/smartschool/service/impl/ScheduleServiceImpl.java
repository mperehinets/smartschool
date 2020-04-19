package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.GenerateScheduleDto;
import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.dto.mapper.ScheduleMapper;
import com.mper.smartschool.dto.mapper.SchoolClassMapper;
import com.mper.smartschool.dto.mapper.TeachersSubjectMapper;
import com.mper.smartschool.entity.Schedule;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.exception.TeacherIsBusyException;
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
        //Loop from start date to end date
        System.out.println(generateScheduleDto.getStartDate());
        System.out.println(generateScheduleDto.getEndDate());
        for (LocalDate date = generateScheduleDto.getStartDate();
             date.isBefore(generateScheduleDto.getEndDate().plusDays(1));
             date = date.plusDays(1)) {
            //Check if weekday
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                final LocalDate finalDate = date;
                //Template schedule for current date
                Collection<TemplateScheduleDto> templatesScheduleDtoForCurrentDate =
                        generateScheduleDto.getTemplatesSchedule()
                                .stream()
                                .filter(item -> item.getDayOfWeek() == finalDate.getDayOfWeek())
                                .collect(Collectors.toList());
                result.addAll(this.templatesScheduleDtoToSchedules(templatesScheduleDtoForCurrentDate,
                        generateScheduleDto.getSchoolClass(),
                        date));
            }

        }
        scheduleRepo.saveAll(result);
        log.info("IN generateSchedule - schedule for class: {} successfully generated, startDate: {}, endDate: {}",
                generateScheduleDto.getSchoolClass(),
                generateScheduleDto.getStartDate(),
                generateScheduleDto.getEndDate());
    }

    @Override
    public void canTeacherHoldLesson(ScheduleDto scheduleDto) {
        Schedule schedule = this.scheduleRepo.findByTeacherIdAndDateAndLessonNumber(
                scheduleDto.getTeachersSubject().getTeacher().getId(),
                scheduleDto.getDate(),
                scheduleDto.getLessonNumber());
        if (schedule != null) {
            throw new TeacherIsBusyException(scheduleDto);
        }
    }

    @Override
    public LocalDate findLastScheduleDateByClassId(Long classId) {
        Schedule schedule = scheduleRepo.findTop1BySchoolClassOrderByDateDesc(
                schoolClassMapper.toEntity(schoolClassService.findById(classId)));
        LocalDate result = schedule == null ? LocalDate.now() : schedule.getDate();
        log.info("IN findLastScheduleDateByClassId - last date found: {}", result);
        return result;
    }

    private Collection<Schedule> templatesScheduleDtoToSchedules(Collection<TemplateScheduleDto> templatesScheduleDto,
                                                                 SchoolClassDto currentSchoolClassDto,
                                                                 LocalDate currentDate) {
        Collection<Schedule> result = new ArrayList<>();
        for (TemplateScheduleDto template : templatesScheduleDto) {
            //Check if teachersSubject exists
            teachersSubjectService.findById(template.getTeachersSubject().getId());

            ScheduleDto scheduleDto = ScheduleDto.builder()
                    .lessonNumber(template.getLessonNumber())
                    .teachersSubject(template.getTeachersSubject())
                    .schoolClass(currentSchoolClassDto)
                    .date(currentDate)
                    .build();

            //Check if teacher can hold lesson
            this.canTeacherHoldLesson(scheduleDto);
            result.add(scheduleMapper.toEntity(scheduleDto));
        }
        return result;
    }
}
