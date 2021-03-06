package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.dto.TeacherDto;
import com.mper.smartschool.dto.mapper.SchoolClassMapper;
import com.mper.smartschool.entity.Schedule;
import com.mper.smartschool.entity.modelsEnum.SchoolClassInitial;
import com.mper.smartschool.exception.ClassHasUnfinishedLessonsException;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.exception.SchoolFilledByClassesException;
import com.mper.smartschool.repository.PupilRepo;
import com.mper.smartschool.repository.ScheduleRepo;
import com.mper.smartschool.repository.SchoolClassRepo;
import com.mper.smartschool.service.SchoolClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchoolClassServiceImpl implements SchoolClassService {

    private static final int YEARS_OF_HISTORY_PRESERVATION = 5;

    private final SchoolClassRepo schoolClassRepo;
    private final SchoolClassMapper schoolClassMapper;
    private final PupilRepo pupilRepo;
    private final ScheduleRepo scheduleRepo;

    @Override
    public boolean fieldValueExists(Object value, String fieldName) {
        try {
            if (fieldName.equals("classTeacher")) {
                findByTeacherId(((TeacherDto) value).getId());
                return true;
            }
            throw new UnsupportedOperationException("Field name not supported");
        } catch (NotFoundException ex) {
            return false;
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SchoolClassDto create(SchoolClassDto schoolClassDto) {
        var lastSchoolClass = schoolClassRepo.findLastByNumber(schoolClassDto.getNumber());
        if (lastSchoolClass == null) {
            schoolClassDto.setInitial(SchoolClassInitial.A);
        } else {
            schoolClassDto.setInitial(lastSchoolClass.getInitial().nextInitial()
                    .orElseThrow(() -> new SchoolFilledByClassesException(schoolClassDto.getNumber())));
        }

        var result = schoolClassMapper.toDto(schoolClassRepo
                .save(schoolClassMapper.toEntity(schoolClassDto)));
        log.info("IN create - schoolClass: {} successfully created", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SchoolClassDto update(SchoolClassDto schoolClassDto) {
        var schoolClassDtoForSave = findById(schoolClassDto.getId());
        schoolClassDtoForSave.setClassTeacher(schoolClassDto.getClassTeacher());

        var result = schoolClassMapper.toDto(schoolClassRepo
                .save(schoolClassMapper.toEntity(schoolClassDtoForSave)));
        log.info("IN update - schoolClass: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<SchoolClassDto> findAll() {
        var result = schoolClassRepo.findAll()
                .stream()
                .map(schoolClassMapper::toDto)
                .filter(schoolClassDto -> schoolClassDto.getNumber() <= 11)
                .peek(SchoolClass -> SchoolClass.setPupilsCount(pupilRepo.countBySchoolClassId(SchoolClass.getId())))
                .peek(SchoolClass -> {
                    var lastSchedule = scheduleRepo.findLastByClassId(SchoolClass.getId());
                    if (lastSchedule != null) {
                        SchoolClass.setLastScheduleDate(lastSchedule.getDate());
                    }
                })
                .collect(Collectors.toList());
        log.info("IN findAll - {} schoolClasses found", result.size());
        return result;
    }

    @Override
    public SchoolClassDto findById(Long id) {
        var result = schoolClassMapper.toDto(schoolClassRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("SchoolClassNotFoundException.byId", id)));
        log.info("IN findById - schoolClass: {} found by id: {}", result, id);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        findById(id);
        schoolClassRepo.deleteById(id);
        log.info("IN deleteById - schoolClass with id: {} successfully deleted", id);
    }

    @Override
    public Collection<SchoolClassDto> findByNumber(Integer number) {
        var result = schoolClassRepo.findByNumber(number)
                .stream()
                .map(schoolClassMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findByNumber - {} schoolClasses found", result.size());
        return result;
    }

    @Override
    public SchoolClassDto findByTeacherId(Long teacherId) {
        var result = schoolClassMapper.toDto(schoolClassRepo.findByTeacherId(teacherId)
                .orElseThrow(() -> new NotFoundException("SchoolClassNotFoundException.byTeacherId", teacherId)));
        log.info("IN findByTeacherId - schoolClass: {} found by id: {}", result, teacherId);
        return result;
    }

    @Override
    public Long getCount() {
        var result = schoolClassRepo.count();
        log.info("IN count - count of schoolClasses: {}", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void moveOnToNewSchoolYear(boolean ignoreSchedule) {
        var schoolClasses = schoolClassRepo.findAll();
        if (!ignoreSchedule) {
            // Classes initials which have unfinished lessons
            var invalidClassesInitials = new StringBuilder();
            // Check if class has unfinished lessons
            for (var sc : schoolClasses) {
                Schedule lastSchedule = scheduleRepo.findFirstBySchoolClassIdOrderByDateDesc(sc.getId());
                if (lastSchedule != null && lastSchedule.getDate().isAfter(LocalDate.now())) {
                    invalidClassesInitials
                            .append(sc.getNumber())
                            .append("-")
                            .append(sc.getInitial())
                            .append(" ");
                }
            }
            if (invalidClassesInitials.length() != 0) {
                throw new ClassHasUnfinishedLessonsException(invalidClassesInitials.toString());
            }
        }

        for (var sc : schoolClasses) {
            // Set new class number
            sc.setNumber(sc.getNumber() + 1);
            // If history about schoolCass hes been kept for 5 years, then mark for removal
            if (sc.getNumber() > YEARS_OF_HISTORY_PRESERVATION + 11) {
                schoolClassRepo.deleteById(sc.getId());
            } else {
                if (sc.getNumber() > 11 && sc.getClassTeacher() != null) {
                    sc.setClassTeacher(null);
                }
                scheduleRepo.deleteByDateAfterAndSchoolClassId(LocalDate.now(), sc.getId());
                schoolClassRepo.save(sc);
            }
        }
    }
}
