package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.dto.mapper.UserMapper;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.User;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.repository.RoleRepo;
import com.mper.smartschool.repository.UserRepo;
import com.mper.smartschool.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final RoleRepo roleRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserMapper userMapper, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDto create(UserDto userDto) {
        Role roleUser = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role not found by name: ROLE_USER"));

        userDto.getRoles().add(roleUser);
        userDto.setStatus(EntityStatus.ACTIVE);

        UserDto result = userMapper.toDto(userRepo.save(userMapper.toEntity(userDto)));

        log.info("IN create - user: {} successfully created", result);

        return result;
    }

    @Override
    public UserDto update(UserDto userDto) {
        findById(userDto.getId());
        UserDto result = userMapper.toDto(userRepo.save(userMapper.toEntity(userDto)));
        log.info("IN update - user: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<UserDto> findAll() {
        Collection<UserDto> result = ((Collection<User>) userRepo.findAll())
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} users found", result.size());
        return result;
    }

    @Override
    public UserDto findById(Long id) {
        UserDto result = userMapper.toDto(userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id: " + id)));
        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        UserDto userDto = findById(id);
        userDto.setStatus(EntityStatus.DELETED);
        userRepo.save(userMapper.toEntity(userDto));
        log.info("IN deleteById - user with id: {} successfully deleted", id);
    }
}
