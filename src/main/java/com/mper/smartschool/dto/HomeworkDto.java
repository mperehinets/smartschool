package com.mper.smartschool.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HomeworkDto extends BaseDto {

    private ScheduleDto schedule;

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
