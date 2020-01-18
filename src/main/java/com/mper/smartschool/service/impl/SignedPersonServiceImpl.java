package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.SignedPersonDto;
import com.mper.smartschool.dto.mapper.SignedPersonMapper;
import com.mper.smartschool.entity.SignedPerson;
import com.mper.smartschool.repository.SignedPersonRepo;
import com.mper.smartschool.service.SignedPersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SignedPersonServiceImpl implements SignedPersonService {

    private final SignedPersonRepo signedPersonRepo;
    private final SignedPersonMapper signedPersonMapper;

    @Autowired
    public SignedPersonServiceImpl(SignedPersonRepo signedPersonRepo, SignedPersonMapper signedPersonMapper) {
        this.signedPersonRepo = signedPersonRepo;
        this.signedPersonMapper = signedPersonMapper;
    }

    @Override
    public SignedPersonDto create(SignedPersonDto signedPersonDto) {
        SignedPersonDto result = signedPersonMapper.toDto(signedPersonRepo
                .save(signedPersonMapper.toEntity(signedPersonDto)));
        log.info("IN create - signedPerson: {} successfully created", result);
        return result;
    }

    @Override
    public SignedPersonDto update(SignedPersonDto signedPersonDto) {
        findById(signedPersonDto.getId());
        SignedPersonDto result = signedPersonMapper.toDto(signedPersonRepo
                .save(signedPersonMapper.toEntity(signedPersonDto)));
        log.info("IN update - signedPerson: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<SignedPersonDto> findAll() {
        Collection<SignedPersonDto> result = ((Collection<SignedPerson>) signedPersonRepo.findAll())
                .stream()
                .map(signedPersonMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} signedPersons found", result.size());
        return result;
    }

    @Override
    public SignedPersonDto findById(Long id) {
        SignedPersonDto result = signedPersonMapper.toDto(signedPersonRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SignedPerson not found by id: " + id)));
        log.info("IN findById - signedPerson: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        signedPersonRepo.deleteById(id);
        log.info("IN deleteById - signedPerson with id: {} successfully deleted", id);
    }
}
