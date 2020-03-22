package com.mper.smartschool.dto.validator;

import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.service.UserService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        try {
            userService.findByEmail(email);
            return false;
        } catch (NotFoundException ex) {
            return true;
        }
    }
}
