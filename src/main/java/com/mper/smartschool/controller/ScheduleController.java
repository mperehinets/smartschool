package com.mper.smartschool.controller;

import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleDto create(@RequestBody ScheduleDto scheduleDto) {
        return scheduleService.create(scheduleDto);
    }

    @PostMapping("/{id}")
    public ScheduleDto update(@PathVariable Long id, @RequestBody ScheduleDto scheduleDto) {
        scheduleDto.setId(id);
        return scheduleService.update(scheduleDto);
    }

    @GetMapping
    public Collection<ScheduleDto> findAll() {
        return scheduleService.findAll();
    }

    @GetMapping("/{id}")
    public ScheduleDto findById(@PathVariable Long id) {
        return scheduleService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        scheduleService.deleteById(id);
    }
}
