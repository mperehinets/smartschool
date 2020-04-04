package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.ChangeStatusDto;
import com.mper.smartschool.dto.SubjectDto;
import com.mper.smartschool.dto.mapper.SubjectMapper;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.SubjectRepo;
import com.mper.smartschool.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepo subjectRepo;
    private final SubjectMapper subjectMapper;

    @Override
    public boolean fieldValueExists(Object value, String fieldName) {
        try {
            if (fieldName.equals("name")) {
                findByName(value.toString());
                return true;
            }
            throw new UnsupportedOperationException("Field name not supported");
        } catch (NotFoundException ex) {
            return false;
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SubjectDto create(SubjectDto subjectDto) {
        subjectDto.setStatus(EntityStatus.ACTIVE);
        SubjectDto result = subjectMapper.toDto(subjectRepo.save(subjectMapper.toEntity(subjectDto)));
        log.info("IN create - subject: {} successfully created", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SubjectDto update(SubjectDto subjectDto) {
        subjectDto.setStatus(findById(subjectDto.getId()).getStatus());
        SubjectDto result = subjectMapper.toDto(subjectRepo.save(subjectMapper.toEntity(subjectDto)));
        log.info("IN update - subject: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<SubjectDto> findAll() {
        Collection<SubjectDto> result = subjectRepo.findAll()
                .stream()
                .map(subjectMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} subjects found", result.size());
        return result;
    }

    @Override
    public SubjectDto findById(Long id) {
        SubjectDto result = subjectMapper.toDto(subjectRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("SubjectNotFoundException.byId", id)));
        log.info("IN findById - subject: {} found by id: {}", result, id);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void changeStatusById(ChangeStatusDto changeStatusDto) {
        findById(changeStatusDto.getId());
        subjectRepo.changeStatusById(changeStatusDto.getId(), changeStatusDto.getNewStatus());
        log.info("IN changeStatusById - subject with id: {} successfully got new status: {}",
                changeStatusDto.getId(),
                changeStatusDto.getNewStatus());
    }

    @Override
    public SubjectDto findByName(String name) {
        SubjectDto result = subjectMapper.toDto(subjectRepo.findByName(name)
                .orElseThrow(() -> new NotFoundException("SubjectNotFoundException.byName", name)));
        log.info("IN findByName - subject: {} found by name: {}", result, name);
        return result;
    }

    @Override
    public Long getCount() {
        Long result = subjectRepo.count();
        log.info("IN count - count of subjects: {}", result);
        return result;
    }

    @Override
    public Collection<SubjectDto> findByStatus(EntityStatus status) {
        Collection<SubjectDto> result = subjectRepo.findByStatus(status)
                .stream()
                .map(subjectMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findByStatus - {} subjects found", result.size());
        return result;
    }

    @Override
    public Collection<SubjectDto> findByTeacherId(Long teacherId) {
        Collection<SubjectDto> result = subjectRepo.findByTeacherIdAndStatus(teacherId, EntityStatus.ACTIVE)
                .stream()
                .map(subjectMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findByStatus - {} subjects found", result.size());
        return result;
    }
}
