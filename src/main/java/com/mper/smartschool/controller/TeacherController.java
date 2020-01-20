package com.mper.smartschool.controller;

import com.mper.smartschool.dto.TeacherDto;
import com.mper.smartschool.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDto create(@RequestBody TeacherDto teacherDto) {
        return teacherService.create(teacherDto);
    }

    @PutMapping("/{id}")
    public TeacherDto update(@PathVariable Long id, @RequestBody TeacherDto teacherDto) {
        teacherDto.setId(id);
        return teacherService.update(teacherDto);
    }

    @GetMapping
    public Collection<TeacherDto> findAll() {
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    public TeacherDto findById(@PathVariable Long id) {
        return teacherService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        teacherService.deleteById(id);
    }
}
