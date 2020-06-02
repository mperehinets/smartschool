package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.GenerateScheduleDto;
import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.dto.mapper.SchoolClassMapper;
import com.mper.smartschool.dto.mapper.TeachersSubjectMapper;
import com.mper.smartschool.entity.Schedule;
import com.mper.smartschool.exception.TeacherIsBusyException;
import com.mper.smartschool.repository.ScheduleRepo;
import com.mper.smartschool.service.ScheduleGeneratorService;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleGeneratorServiceImpl implements ScheduleGeneratorService {

    private final ScheduleRepo scheduleRepo;
    private final SchoolClassService schoolClassService;
    private final TeachersSubjectService teachersSubjectService;
    private final TeachersSubjectMapper teachersSubjectMapper;
    private final SchoolClassMapper schoolClassMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void generateSchedule(GenerateScheduleDto generateScheduleDto) {
        validateGenerateScheduleDto(generateScheduleDto);
        Collection<Schedule> result = new ArrayList<>();
        //Loop from start date to end date
        for (var date = generateScheduleDto.getStartDate();
             date.isBefore(generateScheduleDto.getEndDate().plusDays(1));
             date = date.plusDays(1)) {
            //Check if weekday
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                //Make finalDate for lambda expression
                final var finalDate = date;
                //Template schedule for current date
                generateScheduleDto.getTemplatesSchedule()
                        .stream()
                        .filter(template -> template.getDayOfWeek() == finalDate.getDayOfWeek())
                        .forEach(template -> result.add(Schedule.builder()
                                .lessonNumber(template.getLessonNumber())
                                .teachersSubject(teachersSubjectMapper.toEntity(template.getTeachersSubject()))
                                .schoolClass(schoolClassMapper.toEntity(generateScheduleDto.getSchoolClass()))
                                .date(finalDate)
                                .build()));
            }

        }
        scheduleRepo.saveAll(result);
        log.info("IN generateSchedule - schedule for class: {} successfully generated, startDate: {}, endDate: {}",
                generateScheduleDto.getSchoolClass(),
                generateScheduleDto.getStartDate(),
                generateScheduleDto.getEndDate());
    }

    @Override
    public Boolean canTeacherHoldLesson(TemplateScheduleDto templateScheduleDto,
                                        LocalDate startDate,
                                        LocalDate endDate) {
        var schedule =
                this.scheduleRepo.findByStartDateAndEndDateAndTeacherIdAndLessonNumberAndDayOfWeek(startDate,
                        endDate,
                        templateScheduleDto.getTeachersSubject().getTeacher().getId(),
                        templateScheduleDto.getLessonNumber(),
                        templateScheduleDto.getDayOfWeek().getValue() + 1);
        Boolean result = schedule.isEmpty();
        log.info("IN canTeacherHoldLesson - result: {}", result);
        return result;
    }

    private void validateGenerateScheduleDto(GenerateScheduleDto generateScheduleDto) {
        //Check if school class exists
        schoolClassService.findById(generateScheduleDto.getSchoolClass().getId());
        //Validate start date
        var currentDate = LocalDate.now();
        if (generateScheduleDto.getStartDate().isBefore(currentDate)) {
            generateScheduleDto.setStartDate(currentDate);
        }
        //Loop templatesSchedule
        for (var template : generateScheduleDto.getTemplatesSchedule()) {
            teachersSubjectService.findById(template.getTeachersSubject().getId());
            //Check if teacher can hold lesson
            if (!canTeacherHoldLesson(template, generateScheduleDto.getStartDate(), generateScheduleDto.getEndDate())) {
                throw new TeacherIsBusyException(template.getTeachersSubject().getTeacher(),
                        null,
                        template.getDayOfWeek(),
                        template.getLessonNumber());
            }
        }
    }
}
