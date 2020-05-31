package com.mper.smartschool.service;

import com.mper.smartschool.dto.MailDto;
import com.mper.smartschool.dto.MailInlineImageDto;
import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.entity.PasswordResetToken;

import java.util.List;

public interface EmailService {
    void sendSimpleMessage(MailDto mailDto);

    void sendHtmlMessage(MailDto mailDto, String htmlFileName, List<MailInlineImageDto> images);

    void sendLoginDetails(UserDto userDto);

    void sendResetToken(PasswordResetToken passwordResetToken);
}
