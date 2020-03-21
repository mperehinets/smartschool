package com.mper.smartschool.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResetPasswordDto extends BaseDto {
    @NotNull(message = "{userDto.password.notnull}")
    @Size(min = 8, max = 32, message = "{userDto.password.size}")
    private String newPassword;

    @Builder
    public ResetPasswordDto(Long id, String newPassword) {
        super(id);
        this.newPassword = newPassword;
    }
}
