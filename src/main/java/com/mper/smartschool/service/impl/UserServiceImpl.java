package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.ResetPasswordDto;
import com.mper.smartschool.dto.UpdateAvatarDto;
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

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto create(UserDto userDto) {
        Role roleUser = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new NotFoundException("RoleNotFoundException.byName", "ROLE_USER"));

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRoles(Collections.singleton(roleUser));
        userDto.setStatus(EntityStatus.ACTIVE);
        avatarStorageService.resolveAvatar(userDto);

        UserDto result = userMapper.toDto(userRepo.save(userMapper.toEntity(userDto)));

        log.info("IN create - user: {} successfully created", result);

        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto update(UserDto userDto) {
        UserDto foundUser = findById(userDto.getId());
        userDto.setEmail(foundUser.getEmail());
        userDto.setRoles(foundUser.getRoles());
        userDto.setStatus(foundUser.getStatus());
        avatarStorageService.resolveAvatar(userDto);
        UserDto result = userMapper.toDto(userRepo.save(userMapper.toEntity(userDto)));
        log.info("IN update - user: {} successfully updated", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<UserDto> findAll() {
        Collection<UserDto> result = userRepo.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} users found", result.size());
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto findById(Long id) {
        UserDto result = userMapper.toDto(userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("UserNotFoundException.byId", id)));
        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        findById(id);
        userRepo.setDeletedStatusById(id);
        log.info("IN deleteById - user with id: {} successfully deleted", id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void activateById(Long id) {
        findById(id);
        userRepo.setActiveStatusById(id);
        log.info("IN activateById - user with id: {} successfully activated", id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateById(Long id) {
        findById(id);
        userRepo.setNotActiveStatusById(id);
        log.info("IN deactivateById - user with id: {} successfully deactivated", id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto findByEmail(String email) {
        UserDto result = userMapper.toDto(userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("UserNotFoundException.byEmail", email)));
        log.info("IN findByEmail - user: {} found by email: {}", result, email);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto giveAdminById(Long id) {
        UserDto userDto = findById(id);
        Role roleAdmin = roleRepo.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new NotFoundException("RoleNotFoundException.byName", "ROLE_ADMIN"));
        userDto.getRoles().add(roleAdmin);

        UserDto result = userMapper.toDto(userRepo.save(userMapper.toEntity(userDto)));

        log.info("IN giveAdminById - user: {} successfully got role ADMIN", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto takeAdminAwayById(Long id) {
        UserDto userDto = findById(id);
        Role roleAdmin = roleRepo.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new NotFoundException("RoleNotFoundException.byName", "ROLE_ADMIN"));
        userDto.getRoles().remove(roleAdmin);

        UserDto result = userMapper.toDto(userRepo.save(userMapper.toEntity(userDto)));

        log.info("IN takeAdminAwayById - user: {} successfully lost role ADMIN", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void resetPasswordByAdmin(ResetPasswordDto resetPasswordDto) {
        findById(resetPasswordDto.getId());
        userRepo.updatePasswordById(resetPasswordDto.getId(),
                passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        log.info("IN resetPasswordByAdmin - user with id: {} got new password", resetPasswordDto.getId());
    }

    @Override
    public void updateAvatarById(UpdateAvatarDto updateAvatarDto) {
        findById(updateAvatarDto.getId());

        updateAvatarDto.setNewAvatarName(avatarStorageService.resolveAvatar(updateAvatarDto.getNewAvatarName()));

        userRepo.updateAvatarNameById(updateAvatarDto.getId(), updateAvatarDto.getNewAvatarName());
        log.info("IN updateAvatarById - user with id: {} got new avatar: {}",
                updateAvatarDto.getId(),
                updateAvatarDto.getNewAvatarName());
    }
}
