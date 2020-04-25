package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ScheduleDto extends BaseDto {

    @FutureOrPresent(groups = {OnCreate.class}, message = "{scheduleDto.date.future}")
    private LocalDate date;

    @Min(groups = {OnCreate.class, OnUpdate.class},
            value = 1,
            message = "{scheduleDto.lessonNumber.min}")
    @Max(groups = {OnCreate.class, OnUpdate.class},
            value = 10,
            message = "{scheduleDto.lessonNumber.max}")
    private Integer lessonNumber;

    @NotNull(groups = {OnCreate.class},
            message = "{scheduleDto.schoolClass.notnull}")
    private SchoolClassDto schoolClass;

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{scheduleDto.teachersSubject.notnull}")
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
