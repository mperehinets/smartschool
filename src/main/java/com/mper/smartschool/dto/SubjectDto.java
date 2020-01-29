package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubjectDto extends BaseDto {

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{subjectDto.name.notnull}")
    @Pattern(groups = {OnCreate.class, OnUpdate.class},
            regexp = "[A-ZА-ЯІ][A-Za-zА-Яа-яіІ\\- ]{2,60}",
            message = "{subjectDto.name.pattern}")
    private String name;

    private EntityStatus status;

    public SubjectDto() {
    }

    @Builder
    public SubjectDto(Long id, String name, EntityStatus status) {
        super(id);
        this.name = name;
        this.status = status;
    }
}
