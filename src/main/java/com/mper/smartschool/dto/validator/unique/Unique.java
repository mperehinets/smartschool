package com.mper.smartschool.dto.validator.unique;

import com.mper.smartschool.service.FieldValueExistsService;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UniqueValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface Unique {
    String message() default "Field must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends FieldValueExistsService> service();

    String serviceQualifier() default "";

    String fieldName();
}
