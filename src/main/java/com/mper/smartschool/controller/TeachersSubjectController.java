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
@RequestMapping("/smartschool/teachers-subjects")
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

    @DeleteMapping("/{teacherId}/delete-subject/{subjectId}")
    public TeachersSubjectDto delete(@PathVariable Long teacherId, @PathVariable Long subjectId) {
        return teachersSubjectService.delete(teacherId, subjectId);
    }

    //Following methods without tests
    @GetMapping("/by-teacher-and-subject/{teacherId}/{subjectId}")
    public TeachersSubjectDto findByTeacherIdAndSubjectId(@PathVariable Long teacherId, @PathVariable Long subjectId) {
        return teachersSubjectService.findByTeacherIdAndSubjectId(teacherId, subjectId);
    }

    @GetMapping("/count-by-teacher/{teacherId}")
    public Integer countByTeacherId(@PathVariable Long teacherId) {
        return teachersSubjectService.countByTeacherId(teacherId);
    }
}
