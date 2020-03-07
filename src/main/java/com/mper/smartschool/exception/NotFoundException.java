package com.mper.smartschool.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final Object byWhat;

    public NotFoundException(String message, Object byWhat) {
        super(message);
        this.byWhat = byWhat;
    }
}
