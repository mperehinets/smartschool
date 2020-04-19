package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnGenerateSchedule;
import com.mper.smartschool.dto.transfer.OnUpdate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TemplateScheduleDto extends BaseDto {

    @Min(groups = {OnCreate.class, OnGenerateSchedule.class},
            value = 1,
            message = "{templateScheduleDto.classNumber.min}")
    @Max(groups = {OnCreate.class, OnGenerateSchedule.class},
            value = 11,
            message = "{templateScheduleDto.classNumber.max}")
    private Integer classNumber;

    @NotNull(groups = {OnCreate.class, OnGenerateSchedule.class},
            message = "{templateScheduleDto.dayOfWeek.notnull}")
    private DayOfWeek dayOfWeek;

    @Min(groups = {OnCreate.class, OnUpdate.class, OnGenerateSchedule.class},
            value = 1,
            message = "{templateScheduleDto.lessonNumber.min}")
    @Max(groups = {OnCreate.class, OnUpdate.class, OnGenerateSchedule.class},
            value = 10,
            message = "{templateScheduleDto.lessonNumber.max}")
    private Integer lessonNumber;

    @NotNull(groups = {OnCreate.class, OnUpdate.class, OnGenerateSchedule.class},
            message = "{templateScheduleDto.subject.notnull}")
    private TeachersSubjectDto teachersSubject;

    public TemplateScheduleDto() {
    }

    @Builder
    public TemplateScheduleDto(Long id,
                               Integer classNumber,
                               DayOfWeek dayOfWeek,
                               Integer lessonNumber,
                               TeachersSubjectDto teachersSubject) {
        super(id);
        this.classNumber = classNumber;
        this.dayOfWeek = dayOfWeek;
        this.lessonNumber = lessonNumber;
        this.teachersSubject = teachersSubject;
    }
}
