package com.mper.smartschool.exception;

import lombok.Getter;

@Getter
public class SchoolFilledByClassesException extends RuntimeException {

    private final int classNumber;

    public SchoolFilledByClassesException(int classNumber) {
        super(String.format("School filled by classes number: %d", classNumber));
        this.classNumber = classNumber;
    }
}
