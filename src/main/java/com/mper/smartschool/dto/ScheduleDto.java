package com.mper.smartschool.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleDto extends BaseDto {

    private LocalDate date;

    private Integer lessonNumber;

    private SchoolClassDto schoolClass;

    private TeachersSubjectDto teachersSubject;
}
