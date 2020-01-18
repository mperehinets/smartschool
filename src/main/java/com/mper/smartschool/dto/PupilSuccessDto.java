package com.mper.smartschool.dto;

import com.mper.smartschool.entity.modelsEnum.PupilsLessonStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PupilSuccessDto extends BaseDto {

    private PupilDto pupil;

    private ScheduleDto schedule;

    private Integer rating;

    private PupilsLessonStatus pupilsLessonStatus;

    public PupilSuccessDto() {
    }

    @Builder
    public PupilSuccessDto(Long id,
                           PupilDto pupil,
                           ScheduleDto schedule,
                           Integer rating,
                           PupilsLessonStatus pupilsLessonStatus) {
        super(id);
        this.pupil = pupil;
        this.schedule = schedule;
        this.rating = rating;
        this.pupilsLessonStatus = pupilsLessonStatus;
    }
}
