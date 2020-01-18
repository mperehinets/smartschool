package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.PupilDto;
import com.mper.smartschool.dto.mapper.PupilMapper;
import com.mper.smartschool.entity.Pupil;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.repository.PupilRepo;
import com.mper.smartschool.repository.RoleRepo;
import com.mper.smartschool.service.PupilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PupilServiceImpl implements PupilService {

    private final PupilRepo pupilRepo;
    private final PupilMapper pupilMapper;
    private final RoleRepo roleRepo;

    @Autowired
    public PupilServiceImpl(PupilRepo pupilRepo, PupilMapper pupilMapper, RoleRepo roleRepo) {
        this.pupilRepo = pupilRepo;
        this.pupilMapper = pupilMapper;
        this.roleRepo = roleRepo;
    }

    @Override
    public PupilDto create(PupilDto pupilDto) {
        Role rolePupil = roleRepo.findByName("ROLE_PUPIL")
                .orElseThrow(() -> new EntityNotFoundException("Role not found by name: ROLE_PUPIL"));

        pupilDto.getRoles().add(rolePupil);
        pupilDto.setStatus(EntityStatus.ACTIVE);

        PupilDto result = pupilMapper.toDto(pupilRepo.save(pupilMapper.toEntity(pupilDto)));

        log.info("IN create - pupil: {} successfully created", result);

        return result;
    }

    @Override
    public PupilDto update(PupilDto pupilDto) {
        findById(pupilDto.getId());
        PupilDto result = pupilMapper.toDto(pupilRepo.save(pupilMapper.toEntity(pupilDto)));
        log.info("IN update - pupil: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<PupilDto> findAll() {
        Collection<PupilDto> result = ((Collection<Pupil>) pupilRepo.findAll())
                .stream()
                .map(pupilMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} pupils found", result.size());
        return result;
    }

    @Override
    public PupilDto findById(Long id) {
        PupilDto result = pupilMapper.toDto(pupilRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pupil not found by id: " + id)));
        log.info("IN findById - pupil: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        PupilDto pupilDto = findById(id);
        pupilDto.setStatus(EntityStatus.DELETED);
        pupilRepo.save(pupilMapper.toEntity(pupilDto));
        log.info("IN deleteById - pupil with id: {} successfully deleted", id);
    }
}
