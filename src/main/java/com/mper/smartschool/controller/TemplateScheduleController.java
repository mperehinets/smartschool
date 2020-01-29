package com.mper.smartschool.controller;

import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.service.TemplateScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/templateSchedules")
public class TemplateScheduleController {

    private final TemplateScheduleService templateScheduleService;

    @Autowired
    public TemplateScheduleController(TemplateScheduleService templateScheduleService) {
        this.templateScheduleService = templateScheduleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateScheduleDto create(@Validated(OnCreate.class) @RequestBody TemplateScheduleDto templateScheduleDto) {
        return templateScheduleService.create(templateScheduleDto);
    }

    @PutMapping("/{id}")
    public TemplateScheduleDto update(@PathVariable Long id,
                                      @Validated(OnUpdate.class) @RequestBody TemplateScheduleDto templateScheduleDto) {
        templateScheduleDto.setId(id);
        return templateScheduleService.update(templateScheduleDto);
    }

    @GetMapping
    public Collection<TemplateScheduleDto> findAll() {
        return templateScheduleService.findAll();
    }

    @GetMapping("/{id}")
    public TemplateScheduleDto findById(@PathVariable Long id) {
        return templateScheduleService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        templateScheduleService.deleteById(id);
    }
}
