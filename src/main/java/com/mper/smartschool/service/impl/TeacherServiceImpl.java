package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.TeacherDto;
import com.mper.smartschool.dto.mapper.TeacherMapper;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.RoleRepo;
import com.mper.smartschool.repository.TeacherRepo;
import com.mper.smartschool.repository.TeachersSubjectRepo;
import com.mper.smartschool.service.AvatarStorageService;
import com.mper.smartschool.service.EmailService;
import com.mper.smartschool.service.TeacherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final TeachersSubjectRepo teachersSubjectRepo;
    private final PasswordEncoder passwordEncoder;
    private final AvatarStorageService avatarStorageService;
    private final EmailService emailService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public TeacherDto create(TeacherDto teacherDto) {
        var teacher = teacherMapper.toEntity(teacherDto);
        teacher.setRoles(Collections.singleton(roleRepo.findTeacherRole()));
        teacher.setStatus(EntityStatus.ACTIVE);
        teacher.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
        teacher.setAvatarName(avatarStorageService.resolveAvatar(teacherDto.getAvatarName()));

        var result = teacherMapper.toDto(teacherRepo.save(teacher));

        emailService.sendLoginDetails(teacherDto);

        log.info("IN create - teacher: {} successfully created", result);
        return result;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public TeacherDto update(TeacherDto teacherDto) {
        var foundTeacherDto = findById(teacherDto.getId());
        var teacher = teacherMapper.toEntity(teacherDto);
        teacher.setEmail(foundTeacherDto.getEmail());
        teacher.setRoles(foundTeacherDto.getRoles());
        teacher.setStatus(foundTeacherDto.getStatus());
        teacher.setAvatarName(avatarStorageService.resolveAvatar(teacherDto.getAvatarName()));

        var result = teacherMapper.toDto(teacherRepo.save(teacher));

        log.info("IN update - teacher: {} successfully updated", result);
        return result;
    }

    @Override
    public Collection<TeacherDto> findAll() {
        var result = teacherRepo.findAll()
                .stream()
                .map(teacherMapper::toDto)
                .peek(item -> item.setSubjectsCount(teachersSubjectRepo
                        .countByTeacherIdAndStatus(item.getId(), EntityStatus.ACTIVE)))
                .collect(Collectors.toList());
        log.info("IN findAll - {} teachers found", result.size());
        return result;
    }

    @Override
    public TeacherDto findById(Long id) {
        var result = teacherMapper.toDto(teacherRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("TeacherNotFoundException.byId", id)));
        log.info("IN findById - teacher: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public Long getCount() {
        Long result = teacherRepo.count();
        log.info("IN count - count of teachers: {}", result);
        return result;
    }

    @Override
    public Collection<TeacherDto> findFree() {
        var result = teacherRepo.findFree()
                .stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findFree - {} teachers found", result.size());
        return result;
    }

    @Override
    public Collection<TeacherDto> findBySubjectId(Long subjectId) {
        var result = teacherRepo.findBySubjectId(subjectId)
                .stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findBySubjectId - {} teachers found", result.size());
        return result;
    }
}
