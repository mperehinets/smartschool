package com.mper.smartschool.controller;

import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleDto create(@Validated(OnCreate.class) @RequestBody ScheduleDto scheduleDto) {
        return scheduleService.create(scheduleDto);
    }

    @PutMapping("/{id}")
    public ScheduleDto update(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody ScheduleDto scheduleDto) {
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
