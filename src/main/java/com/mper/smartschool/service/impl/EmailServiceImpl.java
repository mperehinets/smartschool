package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.MailDto;
import com.mper.smartschool.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Override
    public void sendSimpleMessage(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getMailTo());
        message.setSubject(mailDto.getSubject());
        message.setText(mailDto.getMsg());
        emailSender.send(message);
        log.info("IN sendSimpleMessage - message: {} successfully sent", message);
    }

    @SneakyThrows
    @Override
    public void sendHtmlMessage(MailDto mailDto, String htmlFileName) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariables(mailDto.getProps());

        String html = springTemplateEngine.process(htmlFileName, context);
        helper.setTo(mailDto.getMailTo());
        helper.setSubject(mailDto.getSubject());
        helper.setText(html, true);
        emailSender.send(message);
        log.info("IN sendHtmlMessage - message successfully sent");
    }
}
