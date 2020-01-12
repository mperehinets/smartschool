package com.mper.smartschool.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ScheduleDto extends BaseDto {

    private LocalDate date;

    private Integer lessonNumber;

    private SchoolClassDto schoolClass;

    private TeachersSubjectDto teachersSubject;

    public ScheduleDto() {
    }

    @Builder
    public ScheduleDto(Long id,
                       LocalDate date,
                       Integer lessonNumber,
                       SchoolClassDto schoolClass,
                       TeachersSubjectDto teachersSubject) {
        super(id);
        this.date = date;
        this.lessonNumber = lessonNumber;
        this.schoolClass = schoolClass;
        this.teachersSubject = teachersSubject;
    }
}
