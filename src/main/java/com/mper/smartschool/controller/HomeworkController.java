package com.mper.smartschool.controller;

import com.mper.smartschool.dto.HomeworkDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.service.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/homeworks")
public class HomeworkController {

    private final HomeworkService homeworkService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HomeworkDto create(@Validated(OnCreate.class) @RequestBody HomeworkDto homeworkDto) {
        return homeworkService.create(homeworkDto);
    }

    @PutMapping("/{id}")
    public HomeworkDto update(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody HomeworkDto homeworkDto) {
        homeworkDto.setId(id);
        return homeworkService.update(homeworkDto);
    }

    @GetMapping
    public Collection<HomeworkDto> findAll() {
        return homeworkService.findAll();
    }

    @GetMapping("/{id}")
    public HomeworkDto findById(@PathVariable Long id) {
        return homeworkService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        homeworkService.deleteById(id);
    }
}
