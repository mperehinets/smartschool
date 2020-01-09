package com.mper.smartschool.dto;

import com.mper.smartschool.model.Role;
import com.mper.smartschool.model.modelsEnum.EntityStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseDto {

    private String firstName;

    private String secondName;

    private String email;

    private String password;

    private LocalDate dateBirth;

    private List<Role> roles;

    private EntityStatus status;
}
