package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TemplateScheduleDto extends BaseDto {

    @Min(groups = {OnCreate.class},
            value = 1,
            message = "{templateScheduleDto.classNumber.min}")
    @Max(groups = {OnCreate.class},
            value = 11,
            message = "{templateScheduleDto.classNumber.max}")
    private Integer classNumber;

    @Min(groups = {OnCreate.class, OnUpdate.class},
            value = 1,
            message = "{templateScheduleDto.lessonNumber.min}")
    @Max(groups = {OnCreate.class, OnUpdate.class},
            value = 10,
            message = "{templateScheduleDto.lessonNumber.max}")
    private Integer lessonNumber;

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{templateScheduleDto.subject.notnull}")
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
