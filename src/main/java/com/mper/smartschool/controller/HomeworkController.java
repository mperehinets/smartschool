package com.mper.smartschool.controller;

import com.mper.smartschool.dto.HomeworkDto;
import com.mper.smartschool.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/homeworks")
public class HomeworkController {

    private final HomeworkService homeworkService;

    @Autowired
    public HomeworkController(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HomeworkDto create(@RequestBody HomeworkDto homeworkDto) {
        return homeworkService.create(homeworkDto);
    }

    @PutMapping("/{id}")
    public HomeworkDto update(@PathVariable Long id, @RequestBody HomeworkDto homeworkDto) {
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
