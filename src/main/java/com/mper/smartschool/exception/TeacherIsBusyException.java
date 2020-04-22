package com.mper.smartschool.exception;

import com.mper.smartschool.dto.TemplateScheduleDto;
import lombok.Getter;

@Getter
public class TeacherIsBusyException extends RuntimeException {

    private final TemplateScheduleDto invalidTemplateScheduleDto;

    public TeacherIsBusyException(TemplateScheduleDto invalidTemplateScheduleDto) {
        super("Invalid templateSchedule. Teacher is busy");
        this.invalidTemplateScheduleDto = invalidTemplateScheduleDto;
    }
}
