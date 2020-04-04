package com.mper.smartschool.controller;

import com.mper.smartschool.dto.TeacherDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDto create(@Validated(OnCreate.class) @RequestBody TeacherDto teacherDto) {
        return teacherService.create(teacherDto);
    }

    @PutMapping("/{id}")
    public TeacherDto update(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody TeacherDto teacherDto) {
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

    //Following methods without tests
    @GetMapping("/count")
    public Long getCount() {
        return teacherService.getCount();
    }

    //Following methods without tests
    @GetMapping("/free")
    public Collection<TeacherDto> findFree() {
        return teacherService.findFree();
    }
}
