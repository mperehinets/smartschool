package com.mper.smartschool.dto;

import com.mper.smartschool.model.modelsEnum.EntityStatus;
import com.mper.smartschool.model.modelsEnum.SchoolClassInitial;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Year;

@Data
@EqualsAndHashCode(callSuper = true)
public class SchoolClassDto extends BaseDto {

    private Integer number;

    private SchoolClassInitial initial;

    private Year year;

    private TeacherDto classTeacher;

    private EntityStatus status;
}
