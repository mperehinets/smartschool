package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.entity.modelsEnum.PupilsLessonStatus;
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
public class PupilSuccessDto extends BaseDto {

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{pupilSuccessDto.pupil.notnull}")
    private PupilDto pupil;

    @NotNull(groups = {OnCreate.class},
            message = "{pupilSuccessDto.schedule.notnull}")
    private ScheduleDto schedule;

    @Min(groups = {OnCreate.class, OnUpdate.class},
            value = 1,
            message = "{pupilSuccessDto.rating.min}")
    @Max(groups = {OnCreate.class, OnUpdate.class},
            value = 12,
            message = "{pupilSuccessDto.rating.max}")
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
