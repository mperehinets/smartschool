package com.mper.smartschool.entity.modelsEnum;

import java.util.Optional;

public enum SchoolClassInitial {
    A, B, C, D, E, F;

    public Optional<SchoolClassInitial> nextInitial() {
        SchoolClassInitial[] initialValues = SchoolClassInitial.values();
        return Optional.ofNullable(ordinal() != initialValues.length - 1 ? initialValues[ordinal() + 1] : null);
    }
}
