package com.mper.smartschool.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ResetPasswordDto {

    private Long id;

    @NotNull(message = "{userDto.password.notnull}")
    @Size(min = 8, max = 32, message = "{userDto.password.size}")
    private String newPassword;
}
