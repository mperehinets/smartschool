package com.mper.smartschool.dto;

import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.entity.modelsEnum.SchoolClassInitial;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SchoolClassDto extends BaseDto {

    private Integer number;

    private SchoolClassInitial initial;

    private String season;

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
