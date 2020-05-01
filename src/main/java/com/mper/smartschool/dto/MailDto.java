package com.mper.smartschool.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
public class MailDto {
    private String mailTo;
    private String subject;
    private String msg;
    private Map<String, Object> props;

    @Builder
    public MailDto(String mailTo, String subject, String msg, Map<String, Object> props) {
        this.mailTo = mailTo;
        this.subject = subject;
        this.msg = msg;
        this.props = props;
    }
}
