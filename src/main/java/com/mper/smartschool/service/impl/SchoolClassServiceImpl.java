package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.dto.mapper.SchoolClassMapper;
import com.mper.smartschool.entity.SchoolClass;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.repository.SchoolClassRepo;
import com.mper.smartschool.service.SchoolClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepo schoolClassRepo;
    private final SchoolClassMapper schoolClassMapper;

    @Autowired
    public SchoolClassServiceImpl(SchoolClassRepo schoolClassRepo, SchoolClassMapper schoolClassMapper) {
        this.schoolClassRepo = schoolClassRepo;
        this.schoolClassMapper = schoolClassMapper;
    }

    @Override
    public SchoolClassDto create(SchoolClassDto schoolClassDto) {
        schoolClassDto.setStatus(EntityStatus.ACTIVE);
        SchoolClassDto result = schoolClassMapper.toDto(schoolClassRepo
                .save(schoolClassMapper.toEntity(schoolClassDto)));
        log.info("IN create - schoolClass: {} successfully created", result);
        return result;
    }

    @Override
    public SchoolClassDto update(SchoolClassDto schoolClassDto) {
        findById(schoolClassDto.getId());
        SchoolClassDto result = schoolClassMapper.toDto(schoolClassRepo
                .save(schoolClassMapper.toEntity(schoolClassDto)));
        log.info("IN update - schoolClass: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<SchoolClassDto> findAll() {
        Collection<SchoolClassDto> result = ((Collection<SchoolClass>) schoolClassRepo.findAll())
                .stream()
                .map(schoolClassMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} schoolClasses found", result.size());
        return result;
    }

    @Override
    public SchoolClassDto findById(Long id) {
        SchoolClassDto result = schoolClassMapper.toDto(schoolClassRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SchoolClass not found by id: " + id)));
        log.info("IN findById - schoolClass: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        SchoolClassDto schoolClassDto = findById(id);
        schoolClassDto.setStatus(EntityStatus.DELETED);
        schoolClassRepo.save(schoolClassMapper.toEntity(schoolClassDto));
        log.info("IN deleteById - schoolClass with id: {} successfully deleted", id);
    }
}
