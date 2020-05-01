package com.mper.smartschool.controller;

import com.mper.smartschool.dto.ResetPasswordDto;
import com.mper.smartschool.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/passwords")
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/reset")
    public void resetPassword(@RequestParam String email,
                              @Validated @RequestBody ResetPasswordDto resetPasswordDto) {
        resetPasswordDto.setUserEmail(email);
        passwordService.resetPassword(resetPasswordDto);
    }

    @PostMapping("/reset-with-checking")
    public void resetPasswordWithChecking(@RequestParam String email,
                                          @Validated @RequestBody ResetPasswordDto resetPasswordDto) {
        resetPasswordDto.setUserEmail(email);
        passwordService.resetPasswordWithChecking(resetPasswordDto);
    }

    @PostMapping("/send-reset-token")
    public void sendResetToken(@RequestParam String email) {
        passwordService.sendResetToken(email);
    }
}
