package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.ChangeStatusDto;
import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.dto.mapper.UserMapper;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.RoleRepo;
import com.mper.smartschool.repository.UserRepo;
import com.mper.smartschool.security.UserPrincipal;
import com.mper.smartschool.service.AvatarStorageService;
import com.mper.smartschool.service.EmailService;
import com.mper.smartschool.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AvatarStorageService avatarStorageService;
    private final EmailService emailService;

    @Override
    public boolean fieldValueExists(Object value, String fieldName) {
        try {
            if (fieldName.equals("email")) {
                findByEmail(value.toString());
                return true;
            }
            throw new UnsupportedOperationException("Field name not supported");
        } catch (NotFoundException ex) {
            return false;
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto create(UserDto userDto) {
        var user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(Collections.singleton(roleRepo.findUserRole()));
        user.setStatus(EntityStatus.ACTIVE);
        user.setAvatarName(avatarStorageService.resolveAvatar(userDto.getAvatarName()));

        var result = userMapper.toDto(userRepo.save(user));

        emailService.sendLoginDetails(userDto);

        log.info("IN create - user: {} successfully created", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto update(UserDto userDto) {
        var foundUserDto = findById(userDto.getId());
        var user = userMapper.toEntity(userDto);
        user.setEmail(foundUserDto.getEmail());
        user.setRoles(foundUserDto.getRoles());
        user.setStatus(foundUserDto.getStatus());
        user.setAvatarName(avatarStorageService.resolveAvatar(foundUserDto.getAvatarName()));

        var result = userMapper.toDto(userRepo.save(user));

        log.info("IN update - user: {} successfully updated", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<UserDto> findAll() {
        var result = userRepo.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} users found", result.size());
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public UserDto findById(Long id) {
        var result = userMapper.toDto(userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("UserNotFoundException.byId", id)));
        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') and authentication.principal.id != #changeStatusDto.id")
    public void changeStatusById(ChangeStatusDto changeStatusDto) {
        findById(changeStatusDto.getId());
        userRepo.changeStatusById(changeStatusDto.getId(), changeStatusDto.getNewStatus());
        log.info("IN changeStatusByID - user with id: {} successfully got new status: {}",
                changeStatusDto.getId(),
                changeStatusDto.getNewStatus());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto findByEmail(String email) {
        var result = userMapper.toDto(userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("UserNotFoundException.byEmail", email)));
        log.info("IN findByEmail - user: {} found by email: {}", result, email);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') and authentication.principal.id != #id")
    public UserDto giveAdminById(Long id) {
        var userDto = findById(id);
        userDto.getRoles().add(roleRepo.findAdminRole());
        var result = userMapper.toDto(userRepo.save(userMapper.toEntity(userDto)));
        log.info("IN giveAdminById - user: {} successfully got role ADMIN", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') and authentication.principal.id != #id")
    public UserDto takeAdminAwayById(Long id) {
        var userDto = findById(id);
        userDto.getRoles().remove(roleRepo.findAdminRole());
        var result = userMapper.toDto(userRepo.save(userMapper.toEntity(userDto)));
        log.info("IN takeAdminAwayById - user: {} successfully lost role ADMIN", result);
        return result;
    }

    @Override
    public void updateAvatarForCurrent(String avatarName) {
        String resolvedAvatarName = avatarStorageService.resolveAvatar(avatarName);
        userRepo.updateAvatarNameById(findCurrent().getId(), resolvedAvatarName);
        log.info("IN updateAvatarForCurrent - current user got new avatar: {}", resolvedAvatarName);
    }

    @Override
    public Long getCount() {
        Long result = userRepo.count();
        log.info("IN count - count of users: {}", result);
        return result;
    }

    @Override
    public UserDto findCurrent() {
        var userPrincipal = (UserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        var result = findById(userPrincipal.getId());
        log.info("IN findCurrent - current user: {} found", result);
        return result;
    }
}
