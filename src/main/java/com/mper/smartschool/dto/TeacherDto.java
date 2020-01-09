package com.mper.smartschool.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherDto extends UserDto {

    private String education;
}
