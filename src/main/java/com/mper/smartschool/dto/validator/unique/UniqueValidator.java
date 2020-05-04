package com.mper.smartschool.dto.validator.unique;

import com.mper.smartschool.service.FieldValueExistsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private final ApplicationContext applicationContext;

    private FieldValueExistsService service;
    private String fieldName;

    @Override
    public void initialize(Unique unique) {
        Class<? extends FieldValueExistsService> clazz = unique.service();
        this.fieldName = unique.fieldName();
        String serviceQualifier = unique.serviceQualifier();

        if (serviceQualifier.equals("")) {
            this.service = this.applicationContext.getBean(clazz);
        } else {
            this.service = this.applicationContext.getBean(serviceQualifier, clazz);
        }
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o == null) {
            return true;
        }
        return !this.service.fieldValueExists(o, fieldName);
    }
}
