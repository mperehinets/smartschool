package com.mper.smartschool.exception;

import com.mper.smartschool.dto.ScheduleDto;
import lombok.Getter;

@Getter
public class TeacherIsBusyException extends RuntimeException {

    private final ScheduleDto scheduleDto;

    public TeacherIsBusyException(ScheduleDto scheduleDto) {
        super(String.format("Teacher: {%s} is busy. Date: %s. LessonNumber: %d",
                scheduleDto.getTeachersSubject().getTeacher(),
                scheduleDto.getDate(),
                scheduleDto.getLessonNumber()));
        this.scheduleDto = scheduleDto;
    }
}
