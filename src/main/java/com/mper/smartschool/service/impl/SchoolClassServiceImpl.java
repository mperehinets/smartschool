package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.dto.TeacherDto;
import com.mper.smartschool.dto.mapper.SchoolClassMapper;
import com.mper.smartschool.entity.modelsEnum.SchoolClassInitial;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.exception.SchoolFilledByClassesException;
import com.mper.smartschool.repository.SchoolClassRepo;
import com.mper.smartschool.service.SchoolClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepo schoolClassRepo;
    private final SchoolClassMapper schoolClassMapper;

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
        SchoolClassDto lastSchoolClass = schoolClassMapper.toDto(schoolClassRepo
                .findTop1ByNumberOrderByInitialDesc(schoolClassDto.getNumber()));
        if (lastSchoolClass == null) {
            schoolClassDto.setInitial(SchoolClassInitial.A);
        } else {
            schoolClassDto.setInitial(lastSchoolClass.getInitial().nextInitial()
                    .orElseThrow(() -> new SchoolFilledByClassesException(schoolClassDto.getNumber())));
        }

        SchoolClassDto result = schoolClassMapper.toDto(schoolClassRepo
                .save(schoolClassMapper.toEntity(schoolClassDto)));
        log.info("IN create - schoolClass: {} successfully created", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SchoolClassDto update(SchoolClassDto schoolClassDto) {
        SchoolClassDto schoolClassDtoForSave = findById(schoolClassDto.getId());
        schoolClassDtoForSave.setClassTeacher(schoolClassDto.getClassTeacher());

        SchoolClassDto result = schoolClassMapper.toDto(schoolClassRepo
                .save(schoolClassMapper.toEntity(schoolClassDtoForSave)));
        log.info("IN update - schoolClass: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<SchoolClassDto> findAll() {
        Collection<SchoolClassDto> result = schoolClassRepo.findAll()
                .stream()
                .map(schoolClassMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} schoolClasses found", result.size());
        return result;
    }

    @Override
    public SchoolClassDto findById(Long id) {
        SchoolClassDto result = schoolClassMapper.toDto(schoolClassRepo.findById(id)
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
        Collection<SchoolClassDto> result = schoolClassRepo.findByNumber(number)
                .stream()
                .map(schoolClassMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findByNumber - {} schoolClasses found", result.size());
        return result;
    }

    @Override
    public SchoolClassDto findByTeacherId(Long teacherId) {
        SchoolClassDto result = schoolClassMapper.toDto(schoolClassRepo.findByTeacherId(teacherId)
                .orElseThrow(() -> new NotFoundException("SchoolClassNotFoundException.byTeacherId", teacherId)));
        log.info("IN findByTeacherId - schoolClass: {} found by id: {}", result, teacherId);
        return result;
    }

    @Override
    public Long getCount() {
        Long result = schoolClassRepo.count();
        log.info("IN count - count of schoolClasses: {}", result);
        return result;
    }
}
