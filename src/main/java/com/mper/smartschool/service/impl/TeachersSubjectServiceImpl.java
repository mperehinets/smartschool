package com.mper.smartschool.service.impl;

import com.mper.smartschool.dto.TeachersSubjectDto;
import com.mper.smartschool.dto.mapper.TeachersSubjectMapper;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.TeachersSubjectRepo;
import com.mper.smartschool.service.TeachersSubjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TeachersSubjectServiceImpl implements TeachersSubjectService {

    private final TeachersSubjectRepo teachersSubjectRepo;
    private final TeachersSubjectMapper teachersSubjectMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public TeachersSubjectDto create(TeachersSubjectDto teachersSubjectDto) {
        teachersSubjectDto.setStartDate(LocalDate.now());
        TeachersSubjectDto result = teachersSubjectMapper.toDto(teachersSubjectRepo
                .save(teachersSubjectMapper.toEntity(teachersSubjectDto)));
        log.info("IN create - teachersSubject: {} successfully created", result);
        return result;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public Collection<TeachersSubjectDto> findAll() {
        Collection<TeachersSubjectDto> result = teachersSubjectRepo.findAll()
                .stream()
                .map(teachersSubjectMapper::toDto)
                .collect(Collectors.toList());
        log.info("IN findAll - {} teachersSubjects found", result.size());
        return result;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public TeachersSubjectDto findById(Long id) {
        TeachersSubjectDto result = teachersSubjectMapper.toDto(teachersSubjectRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("TeachersSubjectNotFoundException.byId", id)));
        log.info("IN findById - teachersSubject: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        teachersSubjectRepo.deleteById(id);
        log.info("IN deleteById - teachersSubject with id: {} successfully deleted", id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void stopTeachSubjectById(Long id) {
        TeachersSubjectDto teachersSubjectDto = findById(id);
        if (teachersSubjectDto.getEndDate() == null) {
            teachersSubjectRepo.stopTeachSubjectById(id);
        }
        log.info("IN stopTeachSubjectById - teachers: {} stopped teach subject: {}. Date: {}",
                teachersSubjectDto.getTeacher(),
                teachersSubjectDto.getSubject(),
                teachersSubjectDto.getEndDate());
    }
}
