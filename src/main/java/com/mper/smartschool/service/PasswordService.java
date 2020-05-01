package com.mper.smartschool.service;

import com.mper.smartschool.dto.ResetPasswordDto;

public interface PasswordService {
    void resetPassword(ResetPasswordDto resetPasswordDto);

    void resetPasswordWithChecking(ResetPasswordDto resetPasswordDto);

    void sendResetToken(String email);
}
