package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.PupilDto;
import com.mper.smartschool.dto.mapper.PupilMapper;
import com.mper.smartschool.entity.Pupil;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.PupilRepo;
import com.mper.smartschool.repository.RoleRepo;
import com.mper.smartschool.service.AvatarStorageService;
import com.mper.smartschool.service.EmailService;
import com.mper.smartschool.service.PupilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PupilServiceImpl implements PupilService {

    private final PupilRepo pupilRepo;
    private final PupilMapper pupilMapper;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AvatarStorageService avatarStorageService;
    private final EmailService emailService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PupilDto create(PupilDto pupilDto) {
        Pupil pupil = pupilMapper.toEntity(pupilDto);
        pupil.setRoles(Collections.singleton(roleRepo.findPupilRole()));
        pupil.setStatus(EntityStatus.ACTIVE);
        pupil.setPassword(passwordEncoder.encode(pupilDto.getPassword()));
        avatarStorageService.resolveAvatar(pupilDto);

        PupilDto result = pupilMapper.toDto(pupilRepo.save(pupil));

        emailService.sendLoginDetails(pupilDto);

        log.info("IN create - pupil: {} successfully created", result);

        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PupilDto update(PupilDto pupilDto) {
        PupilDto foundPupil = findById(pupilDto.getId());
        pupilDto.setEmail(foundPupil.getEmail());
        pupilDto.setRoles(foundPupil.getRoles());
        pupilDto.setStatus(foundPupil.getStatus());
        avatarStorageService.resolveAvatar(pupilDto);
        PupilDto result = pupilMapper.toDto(pupilRepo.save(pupilMapper.toEntity(pupilDto)));
        log.info("IN update - pupil: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<PupilDto> findAll() {
        Collection<PupilDto> result = pupilRepo.findAll()
                .stream()
                .map(pupilMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} pupils found", result.size());
        return result;
    }

    @Override
    public PupilDto findById(Long id) {
        PupilDto result = pupilMapper.toDto(pupilRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("PupilNotFoundException.byId", id)));
        log.info("IN findById - pupil: {} found by id: {}", result, id);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        PupilDto pupilDto = findById(id);
        pupilDto.setStatus(EntityStatus.DELETED);
        pupilRepo.save(pupilMapper.toEntity(pupilDto));
        log.info("IN deleteById - pupil with id: {} successfully deleted", id);
    }

    @Override
    public Long getCount() {
        Long result = pupilRepo.count();
        log.info("IN count - count of pupils: {}", result);
        return result;
    }
}
