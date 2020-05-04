package com.mper.smartschool.dto;

import com.mper.smartschool.dto.validator.password.Password;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ResetPasswordDto {

    @NotBlank(message = "{userDto.email.notblank}")
    @Email(message = "{userDto.email.email}")
    private String userEmail;

    @NotNull(message = "{userDto.password.notnull}")
    @Password
    private String newPassword;

    private String resetToken;

    @Builder
    public ResetPasswordDto(String userEmail, String newPassword, String resetToken) {
        this.userEmail = userEmail;
        this.newPassword = newPassword;
        this.resetToken = resetToken;
    }
}
