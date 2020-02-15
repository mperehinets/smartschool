package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeachersSubjectDto extends BaseDto {
    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{teachersSubjectDto.teacher.notnull}")
    private TeacherDto teacher;

    @NotNull(groups = {OnCreate.class, OnUpdate.class},
            message = "{teachersSubjectDto.subject.notnull}")
    private SubjectDto subject;

    private LocalDate startDate;

    private LocalDate endDate;

    public TeachersSubjectDto() {
    }

    @Builder
    public TeachersSubjectDto(Long id, TeacherDto teacher, SubjectDto subject, LocalDate startDate, LocalDate endDate) {
        super(id);
        this.teacher = teacher;
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
