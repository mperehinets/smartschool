package com.mper.smartschool.dto;

import com.mper.smartschool.model.modelsEnum.EntityStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SubjectDto extends BaseDto {

    private String name;

    private EntityStatus status;
}
