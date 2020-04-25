package com.mper.smartschool.controller;

import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@Validated({OnUpdate.class})
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

    @GetMapping("/last-by-class/{classId}")
    public ScheduleDto findLastByClassId(@PathVariable Long classId) {
        return scheduleService.findLastByClassId(classId);
    }

    @PutMapping
    public Collection<ScheduleDto> updateAll(@RequestBody Collection<@Valid ScheduleDto> schedulesDto) {
        return scheduleService.updateAll(schedulesDto);
    }

    @GetMapping("/by-class/{classId}/and-date/{date}")
    public Collection<ScheduleDto> findByClassIdAndDate(@PathVariable Long classId,
                                                        @PathVariable
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return scheduleService.findByClassIdAndDate(classId, date);
    }

    @GetMapping("/can-teacher-hold-lesson")
    public Boolean canTeacherHoldLesson(@RequestParam Long teacherId,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                        @RequestParam Integer lessonNumber) {
        return scheduleService.canTeacherHoldLesson(teacherId, date, lessonNumber);
    }
}
