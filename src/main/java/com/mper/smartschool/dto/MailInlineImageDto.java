package com.mper.smartschool.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MailInlineImageDto {
    private String name;
    private byte[] bytes;
    private String contentType;

    @Builder
    public MailInlineImageDto(String name, byte[] bytes, String contentType) {
        this.name = name;
        this.bytes = bytes;
        this.contentType = contentType;
    }
}
