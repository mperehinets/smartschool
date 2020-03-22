package com.mper.smartschool.dto.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailUniqueValidator.class)
@Documented
public @interface EmailUnique {
    String message() default "{UserAlreadyExistException}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
