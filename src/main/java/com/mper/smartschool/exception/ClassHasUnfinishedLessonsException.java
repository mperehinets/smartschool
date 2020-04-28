package com.mper.smartschool.exception;

import lombok.Getter;

@Getter
public class ClassHasUnfinishedLessonsException extends RuntimeException {

    private final String invalidClassesInitials;

    public ClassHasUnfinishedLessonsException(String invalidClassesInitials) {
        super(String.format("(%s) classes has unfinished lessons", invalidClassesInitials));
        this.invalidClassesInitials = invalidClassesInitials;
    }
}
