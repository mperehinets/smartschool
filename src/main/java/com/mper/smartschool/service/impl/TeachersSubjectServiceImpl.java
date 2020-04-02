package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.TeachersSubjectDto;
import com.mper.smartschool.dto.mapper.TeachersSubjectMapper;
import com.mper.smartschool.entity.TeachersSubject;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.TeachersSubjectRepo;
import com.mper.smartschool.service.TeachersSubjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TeachersSubjectServiceImpl implements TeachersSubjectService {

    private final TeachersSubjectRepo teachersSubjectRepo;
    private final TeachersSubjectMapper teachersSubjectMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public TeachersSubjectDto create(TeachersSubjectDto teachersSubjectDto) {
        TeachersSubject teachersSubject = teachersSubjectMapper.toEntity(teachersSubjectDto);

        TeachersSubject teachersSubjectForSave = teachersSubjectRepo.
                findByTeacherIdAndSubjectId(teachersSubject.getTeacher().getId(), teachersSubject.getSubject().getId())
                .orElse(teachersSubject);
        teachersSubjectForSave.setStatus(EntityStatus.ACTIVE);

        TeachersSubjectDto result = teachersSubjectMapper.toDto(teachersSubjectRepo
                .save(teachersSubjectForSave));
        log.info("IN create - teachersSubject: {} successfully created", result);
        return result;

    }

    @Override
    public Collection<TeachersSubjectDto> findAll() {
        Collection<TeachersSubjectDto> result = teachersSubjectRepo.findAll()
                .stream()
                .map(teachersSubjectMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} teachersSubjects found", result.size());
        return result;
    }

    @Override
    public TeachersSubjectDto findById(Long id) {
        TeachersSubjectDto result = teachersSubjectMapper.toDto(teachersSubjectRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("TeachersSubjectNotFoundException.byId", id)));
        log.info("IN findById - teachersSubject: {} found by id: {}", result, id);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public TeachersSubjectDto delete(Long teacherId, Long subjectId) {
        TeachersSubject teachersSubjectForSave = teachersSubjectRepo.
                findByTeacherIdAndSubjectId(teacherId, subjectId)
                .orElseThrow(() -> new NotFoundException("TeachersSubjectNotFoundException.byTeacherAndSubject",
                        teacherId + ", " + subjectId));
        teachersSubjectForSave.setStatus(EntityStatus.DELETED);

        TeachersSubjectDto result = teachersSubjectMapper.toDto(teachersSubjectRepo.save(teachersSubjectForSave));
        log.info("IN delete - teachersSubject: {} successfully got deleted status", result);
        return result;
    }
}
