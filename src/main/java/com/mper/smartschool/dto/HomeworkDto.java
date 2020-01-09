package com.mper.smartschool.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HomeworkDto extends BaseDto {

    private ScheduleDto schedule;

    private String homework;
}
