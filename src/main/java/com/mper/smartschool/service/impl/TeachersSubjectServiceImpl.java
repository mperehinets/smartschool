package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.TeachersSubjectDto;
import com.mper.smartschool.dto.mapper.TeachersSubjectMapper;
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
        var teachersSubject = teachersSubjectMapper.toEntity(teachersSubjectDto);

        var teachersSubjectForSave = teachersSubjectRepo.
                findByTeacherIdAndSubjectIdAndStatuses(teachersSubject.getTeacher().getId(),
                        teachersSubject.getSubject().getId(),
                        EntityStatus.ACTIVE,
                        EntityStatus.EXCLUDED,
                        EntityStatus.DELETED)
                .orElse(teachersSubject);
        teachersSubjectForSave.setStatus(EntityStatus.ACTIVE);

        var result = teachersSubjectMapper.toDto(teachersSubjectRepo.save(teachersSubjectForSave));
        log.info("IN create - teachersSubject: {} successfully created", result);
        return result;
    }

    @Override
    public Collection<TeachersSubjectDto> findAll() {
        var result = teachersSubjectRepo.findAll()
                .stream()
                .map(teachersSubjectMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} teachersSubjects found", result.size());
        return result;
    }

    @Override
    public TeachersSubjectDto findById(Long id) {
        var result = teachersSubjectMapper.toDto(teachersSubjectRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("TeachersSubjectNotFoundException.byId", id)));
        log.info("IN findById - teachersSubject: {} found by id: {}", result, id);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public TeachersSubjectDto delete(Long teacherId, Long subjectId) {
        var teachersSubjectForSave = teachersSubjectRepo.
                findByTeacherIdAndSubjectIdAndStatuses(teacherId, subjectId, EntityStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("TeachersSubjectNotFoundException.byTeacherAndSubject",
                        teacherId + ", " + subjectId));
        teachersSubjectForSave.setStatus(EntityStatus.DELETED);

        var result = teachersSubjectMapper.toDto(teachersSubjectRepo.save(teachersSubjectForSave));
        log.info("IN delete - teachersSubject: {} successfully got deleted status", result);
        return result;
    }

    @Override
    public TeachersSubjectDto findByTeacherIdAndSubjectId(Long teacherId, Long subjectId) {
        var result = teachersSubjectMapper
                .toDto(teachersSubjectRepo
                        .findByTeacherIdAndSubjectIdAndStatuses(teacherId, subjectId, EntityStatus.ACTIVE)
                        .orElseThrow(() -> new NotFoundException("TeachersSubjectNotFoundException.byTeacherAndSubject",
                                teacherId + ", " + subjectId)));
        log.info("IN findByTeacherIdAndSubjectId - teachersSubject: {} found by teacherId: {} and subjectId: {}",
                result,
                teacherId,
                subjectId);
        return result;
    }

    @Override
    public Integer countByTeacherId(Long teacherId) {
        Integer result = teachersSubjectRepo.countByTeacherIdAndStatus(teacherId, EntityStatus.ACTIVE);
        log.info("IN countByTeacherId - {} found", result);
        return result;
    }
}
