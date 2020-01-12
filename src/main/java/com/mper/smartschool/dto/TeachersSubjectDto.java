package com.mper.smartschool.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeachersSubjectDto extends BaseDto {

    private TeacherDto teacher;

    private SubjectDto subject;

    private LocalDate startDate;

    private LocalDate endDate;

    public TeachersSubjectDto() {
    }

    @Builder
    public TeachersSubjectDto(Long id, TeacherDto teacher, SubjectDto subject, LocalDate startDate, LocalDate endDate) {
        super(id);
        this.teacher = teacher;
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
