package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HomeworkDto extends BaseDto {

    @NotNull(groups = {OnCreate.class},
            message = "{homeworkDto.schedule.notnull}")
    private ScheduleDto schedule;

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{homeworkDto.homework.notnull}")
    @Pattern(groups = {OnCreate.class, OnUpdate.class},
            regexp = "[A-Za-zА-Яа-яіІїЇєЄ`'\\-.,?!()№ ]{2,60}",
            message = "{homeworkDto.homework.pattern}")
    private String homework;

    public HomeworkDto() {
    }

    @Builder
    public HomeworkDto(Long id, ScheduleDto schedule, String homework) {
        super(id);
        this.schedule = schedule;
        this.homework = homework;
    }
}
