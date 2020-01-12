package com.mper.smartschool.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TemplateScheduleDto extends BaseDto {

    private Integer classNumber;

    private Integer number;

    private TeachersSubjectDto teachersSubject;

    public TemplateScheduleDto() {
    }

    @Builder
    public TemplateScheduleDto(Long id, Integer classNumber, Integer number, TeachersSubjectDto teachersSubject) {
        super(id);
        this.classNumber = classNumber;
        this.number = number;
        this.teachersSubject = teachersSubject;
    }
}
