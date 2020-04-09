package com.mper.smartschool.controller;

import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.service.TemplateScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Validated({OnUpdate.class})
@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/templates-schedule")
public class TemplateScheduleController {

    private final TemplateScheduleService templateScheduleService;

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

    //Following methods without tests
    @GetMapping("/by-class-number/{classNumber}")
    public Collection<TemplateScheduleDto> findById(@PathVariable Integer classNumber) {
        return templateScheduleService.findByClassNumber(classNumber);
    }

    @GetMapping("/count")
    public Long getCount() {
        return templateScheduleService.getCount();
    }

    @PutMapping
    public Collection<TemplateScheduleDto> update(@RequestBody
                                                          Collection<@Valid TemplateScheduleDto> templatesScheduleDto) {
        return templateScheduleService.update(templatesScheduleDto);
    }
}
