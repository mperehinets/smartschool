package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.dto.mapper.SchoolClassMapper;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.entity.modelsEnum.SchoolClassInitial;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.exception.SchoolFilledByClassesException;
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

    private final SchoolClassRepo schoolClassRepo;
    private final SchoolClassMapper schoolClassMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SchoolClassDto create(SchoolClassDto schoolClassDto) {
        String currentSeason = getCurrentSeason();

        SchoolClassDto lastSchoolClass = schoolClassMapper.toDto(schoolClassRepo
                .lastSchoolClassBySeasonAndNumber(currentSeason, schoolClassDto.getNumber()));
        if (lastSchoolClass == null) {
            schoolClassDto.setInitial(SchoolClassInitial.A);
        } else {
            schoolClassDto.setInitial(lastSchoolClass.getInitial().nextInitial()
                    .orElseThrow(() -> new SchoolFilledByClassesException(schoolClassDto.getNumber())));
        }

        schoolClassDto.setSeason(currentSeason);
        schoolClassDto.setStatus(EntityStatus.ACTIVE);

        SchoolClassDto result = schoolClassMapper.toDto(schoolClassRepo
                .save(schoolClassMapper.toEntity(schoolClassDto)));
        log.info("IN create - schoolClass: {} successfully created", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SchoolClassDto update(SchoolClassDto schoolClassDto) {
        findById(schoolClassDto.getId());
        SchoolClassDto result = schoolClassMapper.toDto(schoolClassRepo
                .save(schoolClassMapper.toEntity(schoolClassDto)));
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
        schoolClassRepo.setDeletedStatusById(id);
        log.info("IN deleteById - schoolClass with id: {} successfully deleted", id);
    }

    private String getCurrentSeason() {
        LocalDate now = LocalDate.now();
        return now.getYear() + "-" + (now.getYear() + 1);
    }
}
