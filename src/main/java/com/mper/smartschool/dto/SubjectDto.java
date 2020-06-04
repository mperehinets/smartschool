package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.dto.validator.unique.Unique;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.service.SubjectService;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubjectDto extends BaseDto {

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{subjectDto.name.notnull}")
    @Pattern(groups = {OnCreate.class, OnUpdate.class},
            regexp = "[A-Za-zА-Яа-яіІїЇєЄ`'\\- ]{3,60}",
            message = "{subjectDto.name.pattern}")
    @Unique(groups = {OnCreate.class},
            service = SubjectService.class,
            fieldName = "name",
            message = "{subjectDto.name.unique}")
    private String name;

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{subjectDto.startIntervalClass.notnull}")
    @Min(groups = {OnCreate.class, OnUpdate.class},
            value = 1,
            message = "{subjectDto.startIntervalClass.min}")
    @Max(groups = {OnCreate.class, OnUpdate.class},
            value = 11,
            message = "{subjectDto.startIntervalClass.max}")
    private Integer startClassInterval;

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{subjectDto.endIntervalClass.notnull}")
    @Min(groups = {OnCreate.class, OnUpdate.class},
            value = 1,
            message = "{subjectDto.endIntervalClass.min}")
    @Max(groups = {OnCreate.class, OnUpdate.class},
            value = 11,
            message = "{subjectDto.endIntervalClass.max}")
    private Integer endClassInterval;

    private EntityStatus status;

    public SubjectDto() {
    }

    @Builder
    public SubjectDto(Long id,
                      String name,
                      Integer startClassInterval,
                      Integer endClassInterval,
                      EntityStatus status) {
        super(id);
        this.name = name;
        this.startClassInterval = startClassInterval;
        this.endClassInterval = endClassInterval;
        this.status = status;
    }
}
