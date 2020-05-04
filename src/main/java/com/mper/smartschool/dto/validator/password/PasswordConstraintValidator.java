package com.mper.smartschool.dto.validator.password;

import lombok.RequiredArgsConstructor;
import org.passay.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceResourceBundle;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

    private final MessageSource messageSource;

    @Override
    public void initialize(Password constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return true;
        }
        PasswordValidator validator = new PasswordValidator(
                new ResourceBundleMessageResolver(new MessageSourceResourceBundle(messageSource,
                        LocaleContextHolder.getLocale())),
                Arrays.asList(
                        // at least 8 characters
                        new LengthRule(8, 32),

                        // at least one upper-case character
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),

                        // at least one lower-case character
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),

                        // at least one digit character
                        new CharacterRule(EnglishCharacterData.Digit, 1),

                        // no whitespace
                        new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join("; ", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
