package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.SignedPersonDto;
import com.mper.smartschool.dto.mapper.SignedPersonMapper;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.SignedPersonRepo;
import com.mper.smartschool.service.SignedPersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignedPersonServiceImpl implements SignedPersonService {

    private final SignedPersonRepo signedPersonRepo;
    private final SignedPersonMapper signedPersonMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SignedPersonDto create(SignedPersonDto signedPersonDto) {
        var result = signedPersonMapper.toDto(signedPersonRepo
                .save(signedPersonMapper.toEntity(signedPersonDto)));
        log.info("IN create - signedPerson: {} successfully created", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SignedPersonDto update(SignedPersonDto signedPersonDto) {
        findById(signedPersonDto.getId());
        var result = signedPersonMapper.toDto(signedPersonRepo
                .save(signedPersonMapper.toEntity(signedPersonDto)));
        log.info("IN update - signedPerson: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<SignedPersonDto> findAll() {
        var result = signedPersonRepo.findAll()
                .stream()
                .map(signedPersonMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} signedPersons found", result.size());
        return result;
    }

    @Override
    public SignedPersonDto findById(Long id) {
        var result = signedPersonMapper.toDto(signedPersonRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("SignedPersonNotFoundException.byId", id)));
        log.info("IN findById - signedPerson: {} found by id: {}", result, id);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        findById(id);
        signedPersonRepo.deleteById(id);
        log.info("IN deleteById - signedPerson with id: {} successfully deleted", id);
    }
}
