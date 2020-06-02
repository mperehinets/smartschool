package com.mper.smartschool.exception.hendler;

import com.mper.smartschool.exception.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiError> handleEntityNotFoundException(NotFoundException ex, Locale locale) {
        Object[] args = {ex.getByWhat()};
        String errorMessage = messageSource.getMessage(ex.getMessage(), args, locale);

        var apiError = ApiError.builder()
                .message(errorMessage)
                .status(HttpStatus.NOT_FOUND)
                .errors(Collections.singletonList(errorMessage))
                .build();
        log.error("Entity not found, thrown:", ex);
        return ResponseEntity
                .status(apiError.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(apiError);
    }

    @ExceptionHandler({SchoolFilledByClassesException.class})
    public ResponseEntity<ApiError> handleSchoolFilledByClassesException(SchoolFilledByClassesException ex,
                                                                         Locale locale) {
        Object[] args = {ex.getClassNumber()};
        String errorMessage = messageSource.getMessage("SchoolFilledByClassesException", args, locale);

        var apiError = ApiError.builder()
                .message(errorMessage)
                .status(HttpStatus.FORBIDDEN)
                .errors(Collections.singletonList(errorMessage))
                .build();
        log.error("School filled by classes, thrown:", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({DayFilledByLessonsException.class})
    public ResponseEntity<ApiError> handleDayFilledByLessonsException(DayFilledByLessonsException ex,
                                                                      Locale locale) {
        Object[] args = {ex.getDayOfWeek()};
        String errorMessage = messageSource.getMessage("DayFilledByLessonsException", args, locale);

        var apiError = ApiError.builder()
                .message(errorMessage)
                .status(HttpStatus.FORBIDDEN)
                .errors(Collections.singletonList(errorMessage))
                .build();
        log.error("Day filled by lessons, thrown:", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);

        var apiError = ApiError.builder()
                .message(errorMessage)
                .status(HttpStatus.UNAUTHORIZED)
                .errors(Collections.singletonList(errorMessage))
                .build();
        log.error("Bad credentials, thrown:", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiError> handleBadCredentialsException(AccessDeniedException ex, Locale locale) {
        String errorMessage = messageSource.getMessage("Access.denied", null, locale);

        var apiError = ApiError.builder()
                .message(errorMessage)
                .status(HttpStatus.FORBIDDEN)
                .errors(Collections.singletonList(errorMessage))
                .build();
        log.error("Access denied, thrown:", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({WrongImageTypeException.class})
    public ResponseEntity<ApiError> handleWrongImageTypeException(WrongImageTypeException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);

        var apiError = ApiError.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST)
                .errors(Collections.singletonList(errorMessage))
                .build();
        log.error("Wrong image type, thrown:", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({InvalidPasswordResetTokenException.class})
    public ResponseEntity<ApiError> handleInvalidPasswordResetTokenException(InvalidPasswordResetTokenException ex,
                                                                             Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);

        var apiError = ApiError.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST)
                .errors(Collections.singletonList(errorMessage))
                .build();
        log.error("Invalid password reset token, thrown:", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        var apiError = ApiError.builder()
                .message("Invalid arguments")
                .status(HttpStatus.BAD_REQUEST)
                .errors(errors)
                .build();
        log.error("Bad request. Errors: {}", errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        var violations = ex.getConstraintViolations();

        List<String> errors = new ArrayList<>();
        violations.forEach(violation -> errors.add(violation.getMessage()));
        var apiError = ApiError.builder()
                .message("Invalid arguments")
                .status(HttpStatus.BAD_REQUEST)
                .errors(errors)
                .build();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(ClassHasUnfinishedLessonsException.class)
    public ResponseEntity<Object> handleClassHasUnfinishedLessonsException(ClassHasUnfinishedLessonsException ex,
                                                                           Locale locale) {
        Object[] args = {
                ex.getInvalidClassesInitials()
        };

        String errorMessage = messageSource.getMessage("ClassHasUnfinishedLessonsException", args, locale);

        var apiError = ApiError.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST)
                .errors(Collections.singletonList(errorMessage))
                .build();
        log.error("Some classes have unfinished lessons, thrown:", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({TeacherIsBusyException.class})
    public ResponseEntity<ApiError> handleTeachersIsBusyException(TeacherIsBusyException ex, Locale locale) {
        Object[] args = {
                ex.getTeacherDto().getFirstName(),
                ex.getTeacherDto().getSecondName(),
                ex.getDate() == null ? "" : ex.getDate(),
                ex.getDayOfWeek() == null ? "" : ex.getDayOfWeek(),
                ex.getLessonNumber()
        };
        String errorMessage = messageSource.getMessage("TeacherIsBusyException", args, locale);

        var apiError = ApiError.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST)
                .errors(Collections.singletonList(errorMessage))
                .build();
        log.error("Teacher is busy, thrown:", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex) {
        var apiError = ApiError.builder()
                .message(ex.getLocalizedMessage())
                .status(HttpStatus.BAD_REQUEST)
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
        log.error("Something went wrong, thrown:", ex);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Data
    @Builder
    public static class ApiError {
        private HttpStatus status;
        private String message;
        private Collection<?> errors;
        private Object payload;
    }
}
