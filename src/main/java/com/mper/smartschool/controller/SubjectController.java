package com.mper.smartschool.controller;

import com.mper.smartschool.dto.ChangeStatusDto;
import com.mper.smartschool.dto.SubjectDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
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

    @PutMapping("/change-status/{id}")
    public void changeStatusById(@PathVariable Long id, @RequestBody ChangeStatusDto changeStatusDto) {
        changeStatusDto.setId(id);
        subjectService.changeStatusById(changeStatusDto);
    }

    //Following methods without tests
    @GetMapping("/count")
    public Long getCount() {
        return subjectService.getCount();
    }

    @GetMapping("/")
    public Collection<SubjectDto> findByStatus(@RequestParam("status") EntityStatus status) {
        return subjectService.findByStatus(status);
    }

    @GetMapping("/by-teacher/{teacherId}")
    public Collection<SubjectDto> findByTeacherId(@PathVariable("teacherId") Long teacherId) {
        return subjectService.findByTeacherId(teacherId);
    }

    @GetMapping("/by-class-number-from-templates-schedule/{classNumber}")
    public Collection<SubjectDto> findFromTemplatesScheduleByClassNumber(
            @PathVariable("classNumber") Integer classNumber) {
        return subjectService.findFromTemplatesScheduleByClassNumber(classNumber);
    }

    @GetMapping("/by-class-number/{classNumber}")
    public Collection<SubjectDto> findByClassNumber(@PathVariable("classNumber") Integer classNumber) {
        return subjectService.findByClassNumber(classNumber);
    }
}
