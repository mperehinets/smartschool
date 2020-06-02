package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.PupilSuccessDto;
import com.mper.smartschool.dto.mapper.PupilSuccessMapper;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.PupilSuccessRepo;
import com.mper.smartschool.service.PupilSuccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PupilSuccessServiceImpl implements PupilSuccessService {

    private final PupilSuccessRepo pupilSuccessRepo;
    private final PupilSuccessMapper pupilSuccessMapper;

    @Autowired
    public PupilSuccessServiceImpl(PupilSuccessRepo pupilSuccessRepo, PupilSuccessMapper pupilSuccessMapper) {
        this.pupilSuccessRepo = pupilSuccessRepo;
        this.pupilSuccessMapper = pupilSuccessMapper;
    }

    @Override
    @PreAuthorize("hasRole('TEACHER')")
    public PupilSuccessDto create(PupilSuccessDto pupilSuccessDto) {
        var result = pupilSuccessMapper.toDto(pupilSuccessRepo
                .save(pupilSuccessMapper.toEntity(pupilSuccessDto)));
        log.info("IN create - pupilSuccess: {} successfully created", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('TEACHER')")
    public PupilSuccessDto update(PupilSuccessDto pupilSuccessDto) {
        findById(pupilSuccessDto.getId());
        var result = pupilSuccessMapper.toDto(pupilSuccessRepo
                .save(pupilSuccessMapper.toEntity(pupilSuccessDto)));
        log.info("IN update - pupilSuccess: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<PupilSuccessDto> findAll() {
        var result = pupilSuccessRepo.findAll()
                .stream()
                .map(pupilSuccessMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} pupilSuccesses found", result.size());
        return result;
    }

    @Override
    public PupilSuccessDto findById(Long id) {
        var result = pupilSuccessMapper.toDto(pupilSuccessRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("PupilSuccessNotFoundException.byId", id)));
        log.info("IN findById - pupilSuccess: {} found by id: {}", result, id);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('TEACHER')")
    public void deleteById(Long id) {
        findById(id);
        pupilSuccessRepo.deleteById(id);
        log.info("IN deleteById - pupilSuccess with id: {} successfully deleted", id);
    }
}
