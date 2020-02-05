package com.mper.smartschool.exception;

public class DayFilledByLessonsException extends RuntimeException {
    public DayFilledByLessonsException(String message) {
        super(message);
    }

    public DayFilledByLessonsException(String message, Throwable cause) {
        super(message, cause);
    }
}
