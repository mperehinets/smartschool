package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.PupilSuccessDto;
import com.mper.smartschool.dto.mapper.PupilSuccessMapper;
import com.mper.smartschool.model.PupilSuccess;
import com.mper.smartschool.repository.PupilSuccessRepo;
import com.mper.smartschool.service.PupilSuccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    public PupilSuccessDto create(PupilSuccessDto pupilSuccessDto) {
        PupilSuccessDto result = pupilSuccessMapper.toDto(pupilSuccessRepo
                .save(pupilSuccessMapper.toEntity(pupilSuccessDto)));
        log.info("IN create - pupilSuccess: {} successfully created", result);
        return result;
    }

    @Override
    public PupilSuccessDto update(PupilSuccessDto pupilSuccessDto) {
        PupilSuccessDto result = pupilSuccessMapper.toDto(pupilSuccessRepo
                .save(pupilSuccessMapper.toEntity(pupilSuccessDto)));
        log.info("IN update - pupilSuccess: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<PupilSuccessDto> findAll() {
        Collection<PupilSuccessDto> result = ((Collection<PupilSuccess>) pupilSuccessRepo.findAll())
                .stream()
                .map(pupilSuccessMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} pupilSuccesses found", result.size());
        return result;
    }

    @Override
    public PupilSuccessDto findById(Long id) {
        PupilSuccessDto result = pupilSuccessMapper.toDto(pupilSuccessRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PupilSuccess not found by id: " + id)));
        log.info("IN findById - pupilSuccess: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        pupilSuccessRepo.deleteById(id);
        log.info("IN deleteById - pupilSuccess with id: {} successfully deleted", id);
    }
}
