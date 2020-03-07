package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.dto.mapper.UserMapper;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.RoleRepo;
import com.mper.smartschool.repository.UserRepo;
import com.mper.smartschool.service.AvatarStorageService;
import com.mper.smartschool.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AvatarStorageService avatarStorageService;

    @Override
    public UserDto create(UserDto userDto) {
        Role roleUser = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role not found by name: ROLE_USER"));

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRoles(Collections.singleton(roleUser));
        userDto.setStatus(EntityStatus.ACTIVE);
        avatarStorageService.resolveAvatar(userDto);

        UserDto result = userMapper.toDto(userRepo.save(userMapper.toEntity(userDto)));

        log.info("IN create - user: {} successfully created", result);

        return result;
    }

    @Override
    public UserDto update(UserDto userDto) {
        findById(userDto.getId());
        avatarStorageService.resolveAvatar(userDto);
        UserDto result = userMapper.toDto(userRepo.save(userMapper.toEntity(userDto)));
        log.info("IN update - user: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<UserDto> findAll() {
        Collection<UserDto> result = userRepo.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} users found", result.size());
        return result;
    }

    @Override
    public UserDto findById(Long id) {
        UserDto result = userMapper.toDto(userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("UserNotFoundException.byId", id)));
        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        userRepo.setDeletedStatusById(id);
        log.info("IN deleteById - user with id: {} successfully deleted", id);
    }

    @Override
    public UserDto findByEmail(String email) {
        UserDto result = userMapper.toDto(userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("UserNotFoundException.byEmail", email)));
        log.info("IN findByEmail - user: {} found by email: {}", result, email);
        return result;
    }
}
