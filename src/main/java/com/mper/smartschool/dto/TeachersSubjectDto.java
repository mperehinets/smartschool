package com.mper.smartschool.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeachersSubjectDto extends BaseDto {

    private TeacherDto teacher;

    private SubjectDto subject;

    private LocalDate startDate;

    private LocalDate endDate;
}
