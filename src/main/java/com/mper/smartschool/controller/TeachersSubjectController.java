package com.mper.smartschool.controller;

import com.mper.smartschool.dto.TeachersSubjectDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.service.TeachersSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/teachersSubjects")
public class TeachersSubjectController {

    private final TeachersSubjectService teachersSubjectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeachersSubjectDto create(@Validated(OnCreate.class) @RequestBody TeachersSubjectDto teachersSubjectDto) {
        return teachersSubjectService.create(teachersSubjectDto);
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

    @PutMapping("/stopTeachSubject/{id}")
    public void stopTeachSubjectById(@PathVariable Long id) {
        teachersSubjectService.stopTeachSubjectById(id);
    }
}
