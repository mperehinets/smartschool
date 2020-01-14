package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.SubjectDto;
import com.mper.smartschool.dto.mapper.SubjectMapper;
import com.mper.smartschool.model.Subject;
import com.mper.smartschool.model.modelsEnum.EntityStatus;
import com.mper.smartschool.repository.SubjectRepo;
import com.mper.smartschool.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubjectServiceImpl implements SubjectService {


    private final SubjectRepo subjectRepo;
    private final SubjectMapper subjectMapper;

    @Autowired
    public SubjectServiceImpl(SubjectRepo subjectRepo, SubjectMapper subjectMapper) {
        this.subjectRepo = subjectRepo;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public SubjectDto create(SubjectDto subjectDto) {
        subjectDto.setStatus(EntityStatus.ACTIVE);
        SubjectDto result = subjectMapper.toDto(subjectRepo.save(subjectMapper.toEntity(subjectDto)));
        log.info("IN create - subject: {} successfully created", result);
        return result;
    }

    @Override
    public SubjectDto update(SubjectDto subjectDto) {
        SubjectDto result = subjectMapper.toDto(subjectRepo.save(subjectMapper.toEntity(subjectDto)));
        log.info("IN update - subject: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<SubjectDto> findAll() {
        Collection<SubjectDto> result = ((Collection<Subject>) subjectRepo.findAll())
                .stream()
                .map(subjectMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} subjects found", result.size());
        return result;
    }

    @Override
    public SubjectDto findById(Long id) {
        SubjectDto result = subjectMapper.toDto(subjectRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found by id: " + id)));
        log.info("IN findById - subject: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        SubjectDto subjectDto = findById(id);
        subjectDto.setStatus(EntityStatus.DELETED);
        subjectRepo.save(subjectMapper.toEntity(subjectDto));
        log.info("IN deleteById - subject with id: {} successfully deleted", id);
    }
}
