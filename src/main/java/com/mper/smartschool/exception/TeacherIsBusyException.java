package com.mper.smartschool.exception;

import com.mper.smartschool.dto.TeacherDto;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
public class TeacherIsBusyException extends RuntimeException {

    private final TeacherDto teacherDto;
    private final LocalDate date;
    private final DayOfWeek dayOfWeek;
    private final Integer lessonNumber;

    public TeacherIsBusyException(TeacherDto teacherDto, LocalDate date, DayOfWeek dayOfWeek, Integer lessonNumber) {
        super("Invalid templateSchedule. Teacher is busy");
        this.teacherDto = teacherDto;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.lessonNumber = lessonNumber;
    }
}
