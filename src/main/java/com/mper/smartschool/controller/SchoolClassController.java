package com.mper.smartschool.controller;

import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.service.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/schoolClasses")
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    @Autowired
    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolClassDto create(@RequestBody SchoolClassDto schoolClassDto) {
        return schoolClassService.create(schoolClassDto);
    }

    @PostMapping("/{id}")
    public SchoolClassDto update(@PathVariable Long id, @RequestBody SchoolClassDto schoolClassDto) {
        schoolClassDto.setId(id);
        return schoolClassService.update(schoolClassDto);
    }

    @GetMapping
    public Collection<SchoolClassDto> findAll() {
        return schoolClassService.findAll();
    }

    @GetMapping("/{id}")
    public SchoolClassDto findById(@PathVariable Long id) {
        return schoolClassService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        schoolClassService.deleteById(id);
    }
}
