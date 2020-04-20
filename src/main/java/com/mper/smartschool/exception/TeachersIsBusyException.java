package com.mper.smartschool.exception;

import com.mper.smartschool.dto.TemplateScheduleDto;
import lombok.Getter;

import java.util.Collection;

@Getter
public class TeachersIsBusyException extends RuntimeException {

    private final Collection<TemplateScheduleDto> invalidTemplatesScheduleDto;

    public TeachersIsBusyException(Collection<TemplateScheduleDto> invalidTemplatesScheduleDto) {
        super("Invalid schedule. Teachers is busy");
        this.invalidTemplatesScheduleDto = invalidTemplatesScheduleDto;
    }
}
