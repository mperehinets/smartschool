package com.mper.smartschool.dto;

import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeacherDto extends UserDto {

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
                      Set<Role> roles,
                      EntityStatus status,
                      String education) {
        super(id, firstName, secondName, email, password, dateBirth, roles, status);
        this.education = education;
    }
}
