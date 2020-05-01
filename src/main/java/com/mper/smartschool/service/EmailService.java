package com.mper.smartschool.service;

import com.mper.smartschool.dto.MailDto;

public interface EmailService {
    void sendSimpleMessage(MailDto mailDto);

    void sendHtmlMessage(MailDto mailDto, String htmlFileName);
}
