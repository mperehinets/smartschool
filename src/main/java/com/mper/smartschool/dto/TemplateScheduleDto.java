package com.mper.smartschool.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateScheduleDto extends BaseDto {

    private Integer classNumber;

    private Integer number;

    private TeachersSubjectDto teachersSubject;
}
