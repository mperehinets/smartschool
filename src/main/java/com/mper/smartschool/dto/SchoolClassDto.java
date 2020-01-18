package com.mper.smartschool.dto;

import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.entity.modelsEnum.SchoolClassInitial;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Year;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SchoolClassDto extends BaseDto {

    private Integer number;

    private SchoolClassInitial initial;

    private Year year;

    private TeacherDto classTeacher;

    private EntityStatus status;

    public SchoolClassDto() {
    }

    @Builder
    public SchoolClassDto(Long id,
                          Integer number,
                          SchoolClassInitial initial,
                          Year year,
                          TeacherDto classTeacher,
                          EntityStatus status) {
        super(id);
        this.number = number;
        this.initial = initial;
        this.year = year;
        this.classTeacher = classTeacher;
        this.status = status;
    }
}
