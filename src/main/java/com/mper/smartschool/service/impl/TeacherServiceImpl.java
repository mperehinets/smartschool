package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.TeacherDto;
import com.mper.smartschool.dto.mapper.TeacherMapper;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.RoleRepo;
import com.mper.smartschool.repository.TeacherRepo;
import com.mper.smartschool.service.TeacherService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepo teacherRepo;
    private final TeacherMapper teacherMapper;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public TeacherDto create(TeacherDto teacherDto) {
        Role roleTeacher = roleRepo.findByName("ROLE_TEACHER")
                .orElseThrow(() -> new EntityNotFoundException("Role not found by name: ROLE_TEACHER"));

        teacherDto.setRoles(Collections.singleton(roleTeacher));
        teacherDto.setStatus(EntityStatus.ACTIVE);
        teacherDto.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
        TeacherDto result = teacherMapper.toDto(teacherRepo.save(teacherMapper.toEntity(teacherDto)));

        log.info("IN create - teacher: {} successfully created", result);

        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') and authentication.principal.id == #teacherDto.id")
    public TeacherDto update(TeacherDto teacherDto) {
        findById(teacherDto.getId());
        TeacherDto result = teacherMapper.toDto(teacherRepo.save(teacherMapper.toEntity(teacherDto)));
        log.info("IN update - teacher: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<TeacherDto> findAll() {
        Collection<TeacherDto> result = teacherRepo.findAll()
                .stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} teachers found", result.size());
        return result;
    }

    @Override
    public TeacherDto findById(Long id) {
        TeacherDto result = teacherMapper.toDto(teacherRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("TeacherNotFoundException.byId", id)));
        log.info("IN findById - teacher: {} found by id: {}", result, id);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        TeacherDto teacherDto = findById(id);
        teacherDto.setStatus(EntityStatus.DELETED);
        teacherRepo.save(teacherMapper.toEntity(teacherDto));
        log.info("IN deleteById - teacher with id: {} successfully deleted", id);
    }
}
