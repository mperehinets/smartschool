package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeacherDto extends UserDto {

    @Pattern(groups = {OnCreate.class, OnUpdate.class},
            regexp = "[A-Za-zА-Яа-яіІїЇєЄ`'\\-.,№ ]{0,200}",
            message = "{teacherDto.education.pattern}")
    private String education;

    public TeacherDto() {
    }

    @Builder(builderMethodName = "teacherBuilder")
    public TeacherDto(Long id,
                      String firstName,
                      String secondName,
                      String email,
                      String password,
                      LocalDate dateBirth,
                      String avatarName,
                      Set<Role> roles,
                      EntityStatus status,
                      String education) {
        super(id, firstName, secondName, email, password, dateBirth, avatarName, roles, status);
        this.education = education;
    }
}
