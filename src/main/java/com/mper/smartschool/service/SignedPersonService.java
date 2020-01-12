package com.mper.smartschool.service;

import com.mper.smartschool.dto.SignedPersonDto;

import java.util.Collection;

public interface SignedPersonService {

    SignedPersonDto create(SignedPersonDto signedPersonDto);

    SignedPersonDto update(SignedPersonDto signedPersonDto);

    Collection<SignedPersonDto> findAll();

    SignedPersonDto findById(Long id);

    void deleteById(Long id);
}
