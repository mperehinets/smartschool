package com.mper.smartschool.controller;

import com.mper.smartschool.dto.TeachersSubjectDto;
import com.mper.smartschool.service.TeachersSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/teachersSubjects")
public class TeachersSubjectController {

    private final TeachersSubjectService teachersSubjectService;

    @Autowired
    public TeachersSubjectController(TeachersSubjectService teachersSubjectService) {
        this.teachersSubjectService = teachersSubjectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeachersSubjectDto create(@RequestBody TeachersSubjectDto teachersSubjectDto) {
        return teachersSubjectService.create(teachersSubjectDto);
    }

    @PutMapping("/{id}")
    public TeachersSubjectDto update(@PathVariable Long id, @RequestBody TeachersSubjectDto teachersSubjectDto) {
        teachersSubjectDto.setId(id);
        return teachersSubjectService.update(teachersSubjectDto);
    }

    @GetMapping
    public Collection<TeachersSubjectDto> findAll() {
        return teachersSubjectService.findAll();
    }

    @GetMapping("/{id}")
    public TeachersSubjectDto findById(@PathVariable Long id) {
        return teachersSubjectService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        teachersSubjectService.deleteById(id);
    }
}
