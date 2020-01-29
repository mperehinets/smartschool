package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.entity.modelsEnum.SchoolClassInitial;
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
public class SchoolClassDto extends BaseDto {

    @Min(groups = {OnCreate.class},
            value = 1,
            message = "{schoolClassDto.number.min}")
    @Max(groups = {OnCreate.class},
            value = 11,
            message = "{schoolClassDto.number.max}")
    private Integer number;

    private SchoolClassInitial initial;

    private String season;

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{schoolClassDto.classTeacher.notnull}")
    private TeacherDto classTeacher;

    private EntityStatus status;

    public SchoolClassDto() {
    }

    @Builder
    public SchoolClassDto(Long id,
                          Integer number,
                          SchoolClassInitial initial,
                          String season,
                          TeacherDto classTeacher,
                          EntityStatus status) {
        super(id);
        this.number = number;
        this.initial = initial;
        this.season = season;
        this.classTeacher = classTeacher;
        this.status = status;
    }
}
