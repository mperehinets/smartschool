package com.mper.smartschool.dto;

import com.mper.smartschool.model.Role;
import com.mper.smartschool.model.modelsEnum.EntityStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeacherDto extends UserDto {

    private String education;

    public TeacherDto() {
    }

    @Builder
    public TeacherDto(Long id,
                      String firstName,
                      String secondName,
                      String email,
                      String password,
                      LocalDate dateBirth,
                      List<Role> roles,
                      EntityStatus status,
                      String education) {
        super(id, firstName, secondName, email, password, dateBirth, roles, status);
        this.education = education;
    }
}
