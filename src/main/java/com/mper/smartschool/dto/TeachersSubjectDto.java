package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeachersSubjectDto extends BaseDto {
    @NotNull(groups = {OnCreate.class},
            message = "{teachersSubjectDto.teacher.notnull}")
    private TeacherDto teacher;

    @NotNull(groups = {OnCreate.class},
            message = "{teachersSubjectDto.subject.notnull}")
    private SubjectDto subject;

    private EntityStatus status;

    public TeachersSubjectDto() {
    }

    @Builder
    public TeachersSubjectDto(Long id, TeacherDto teacher, SubjectDto subject, EntityStatus status) {
        super(id);
        this.teacher = teacher;
        this.subject = subject;
        this.status = status;
    }
}
