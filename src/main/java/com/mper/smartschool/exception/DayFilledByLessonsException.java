package com.mper.smartschool.exception;

import lombok.Getter;

import java.time.DayOfWeek;

@Getter
public class DayFilledByLessonsException extends RuntimeException {

    public final DayOfWeek dayOfWeek;

    public DayFilledByLessonsException(DayOfWeek dayOfWeek) {
        super(String.format("%s is filled by lessons for class", dayOfWeek));
        this.dayOfWeek = dayOfWeek;
    }
}
