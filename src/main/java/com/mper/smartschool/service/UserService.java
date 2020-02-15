package com.mper.smartschool.service;

import com.mper.smartschool.dto.UserDto;

import java.util.Collection;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    Collection<UserDto> findAll();

    UserDto findById(Long id);

    void deleteById(Long id);

    UserDto findByEmail(String email);
}
