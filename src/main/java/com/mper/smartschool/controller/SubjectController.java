package com.mper.smartschool.controller;

import com.mper.smartschool.dto.SubjectDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDto create(@Validated(OnCreate.class) @RequestBody SubjectDto subjectDto) {
        return subjectService.create(subjectDto);
    }

    @PutMapping("/{id}")
    public SubjectDto update(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody SubjectDto subjectDto) {
        subjectDto.setId(id);
        return subjectService.update(subjectDto);
    }

    @GetMapping
    public Collection<SubjectDto> findAll() {
        return subjectService.findAll();
    }

    @GetMapping("/{id}")
    public SubjectDto findById(@PathVariable Long id) {
        return subjectService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        subjectService.deleteById(id);
    }
}
