package com.mper.smartschool.dto;

import com.mper.smartschool.model.modelsEnum.PupilsLessonStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PupilSuccessDto extends BaseDto {

    private PupilDto pupil;

    private ScheduleDto schedule;

    private Integer rating;

    private PupilsLessonStatus pupilsLessonStatus;
}
