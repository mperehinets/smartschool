package com.mper.smartschool.service.impl;

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
        findById(subjectDto.getId());
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
    public void deleteById(Long id) {
        findById(id);
        subjectRepo.setDeletedStatusById(id);
        log.info("IN deleteById - subject with id: {} successfully deleted", id);
    }
}
