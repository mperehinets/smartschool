package com.mper.smartschool.dto;

import lombok.Data;

@Data
public abstract class BaseDto {

    private Long id;

    protected BaseDto() {
    }

    protected BaseDto(Long id) {
        this.id = id;
    }
}
