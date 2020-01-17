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

    private Integer lessonNumber;

    private SubjectDto subject;

    public TemplateScheduleDto() {
    }

    @Builder
    public TemplateScheduleDto(Long id, Integer classNumber, Integer lessonNumber, SubjectDto subject) {
        super(id);
        this.classNumber = classNumber;
        this.lessonNumber = lessonNumber;
        this.subject = subject;
    }
}
