package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.ResetPasswordDto;
import com.mper.smartschool.entity.PasswordResetToken;
import com.mper.smartschool.entity.User;
import com.mper.smartschool.exception.InvalidPasswordResetTokenException;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.PasswordResetTokenRepo;
import com.mper.smartschool.repository.UserRepo;
import com.mper.smartschool.service.EmailService;
import com.mper.smartschool.service.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final UserRepo userRepo;
    private final EmailService emailService;
    private final PasswordResetTokenRepo passwordResetTokenRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasRole('ADMIN') and authentication.principal.email != #resetPasswordDto.userEmail")
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        findUserByEmail(resetPasswordDto.getUserEmail());
        userRepo.updatePasswordByEmail(resetPasswordDto.getUserEmail(),
                passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        log.info("IN resetPassword - user with email: {} got new password", resetPasswordDto.getUserEmail());
    }

    @Override
    public void resetPasswordWithChecking(ResetPasswordDto resetPasswordDto) {
        var passwordResetToken = passwordResetTokenRepo.findByToken(resetPasswordDto.getResetToken())
                .orElseThrow(() -> new NotFoundException("PasswordResetTokenNotFound.byToken",
                        resetPasswordDto.getResetToken()));
        if (passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            userRepo.updatePasswordByEmail(passwordResetToken.getUser().getEmail(),
                    passwordEncoder.encode(resetPasswordDto.getNewPassword()));
            log.info("IN resetPasswordWithChecking - user with email: {} got new password",
                    resetPasswordDto.getUserEmail());
        } else {
            throw new InvalidPasswordResetTokenException("PasswordResetTokenException.expired");
        }
        passwordResetTokenRepo.deleteByUserId(passwordResetToken.getUser().getId());
    }

    @Override
    public void sendResetToken(String email) {
        var user = findUserByEmail(email);
        var passwordResetToken = PasswordResetToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(PasswordResetToken.EXPIRATION))
                .build();
        passwordResetTokenRepo.save(passwordResetToken);
        emailService.sendResetToken(passwordResetToken);
    }

    private User findUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("UserNotFoundException.byEmail", email));
    }
}
