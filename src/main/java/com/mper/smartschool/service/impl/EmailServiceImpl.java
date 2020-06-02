package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.MailDto;
import com.mper.smartschool.dto.MailInlineImageDto;
import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.entity.PasswordResetToken;
import com.mper.smartschool.service.AvatarStorageService;
import com.mper.smartschool.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine springTemplateEngine;
    private final AvatarStorageService avatarStorageService;
    private final ResourceLoader resourceLoader;

    @Override
    public void sendSimpleMessage(MailDto mailDto) {
        var message = new SimpleMailMessage();
        message.setTo(mailDto.getMailTo());
        message.setSubject(mailDto.getSubject());
        message.setText(mailDto.getMsg());
        emailSender.send(message);
        log.info("IN sendSimpleMessage - message: {} successfully sent", message);
    }

    @SneakyThrows
    @Override
    public void sendHtmlMessage(MailDto mailDto, String htmlFileName, List<MailInlineImageDto> images) {
        var message = emailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(mailDto.getMailTo());
        helper.setSubject(mailDto.getSubject());

        // Prepare the evaluation context
        var context = new Context();
        context.setVariables(mailDto.getProps());

        // Create the HTML body using Thymeleaf
        String html = springTemplateEngine.process(htmlFileName, context);
        helper.setText(html, true);

        // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
        for (var image : images) {
            helper.addInline(image.getName(), new ByteArrayResource(image.getBytes()), image.getContentType());
        }
        emailSender.send(message);
        log.info("IN sendHtmlMessage - message successfully sent");
    }

    @Override
    public void sendLoginDetails(UserDto userDto) {
        Map<String, Object> props = new HashMap<>();
        props.put("email", userDto.getEmail());
        props.put("password", userDto.getPassword());
        props.put("firstName", userDto.getFirstName());
        props.put("secondName", userDto.getSecondName());

        var mailDto = MailDto.builder()
                .mailTo(userDto.getEmail())
                .subject("Login details")
                .props(props)
                .build();

        List<MailInlineImageDto> images = new ArrayList<>();
        images.add(getInlineAvatarByName(userDto.getAvatarName()));
        images.add(getInlineMainLogo());

        sendHtmlMessage(mailDto, "login-details-letter", images);
        log.info("IN sendLoginDetails - login details successfully sent to email: {}", userDto.getEmail());
    }

    @Override
    public void sendResetToken(PasswordResetToken passwordResetToken) {
        var user = passwordResetToken.getUser();
        Map<String, Object> props = new HashMap<>();
        props.put("token", passwordResetToken.getToken());
        props.put("firstName", user.getFirstName());
        props.put("secondName", user.getSecondName());

        var mailDto = MailDto.builder()
                .mailTo(user.getEmail())
                .subject("Reset password")
                .props(props)
                .build();

        List<MailInlineImageDto> images = new ArrayList<>();
        images.add(getInlineAvatarByName(user.getAvatarName()));
        images.add(getInlineMainLogo());

        sendHtmlMessage(mailDto, "reset-password-letter", images);
        log.info("IN sendResetToken - reset token successfully sent to email: {}", user.getEmail());
    }


    @SneakyThrows
    private MailInlineImageDto getInlineMainLogo() {
        return MailInlineImageDto.builder()
                .name("mainLogo")
                .bytes(resourceLoader.getResource("classpath:mainLogo.png").getInputStream().readAllBytes())
                .contentType(MediaType.IMAGE_PNG_VALUE)
                .build();
    }

    @SneakyThrows
    private MailInlineImageDto getInlineAvatarByName(String avatarName) {
        return MailInlineImageDto.builder()
                .name("avatar")
                .bytes(Files.readAllBytes(avatarStorageService.load(avatarName).getFile().toPath()))
                .contentType(MediaType.IMAGE_PNG_VALUE)
                .build();
    }
}
