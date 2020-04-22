package com.mper.smartschool.controller;

import com.mper.smartschool.dto.GenerateScheduleDto;
import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.dto.transfer.OnGenerateSchedule;
import com.mper.smartschool.service.ScheduleGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/schedule-generator")
public class ScheduleGeneratorController {

    private final ScheduleGeneratorService scheduleGeneratorService;

    //Following methods without tests
    @PostMapping
    public void generateSchedule(@Validated(OnGenerateSchedule.class)
                                 @RequestBody GenerateScheduleDto generateScheduleDto) {
        this.scheduleGeneratorService.generateSchedule(generateScheduleDto);
    }

    @PostMapping("/check-valid")
    public Boolean canTeacherHoldLesson(@RequestBody TemplateScheduleDto templateSchedule,
                                        @RequestParam
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                        @RequestParam
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return this.scheduleGeneratorService.canTeacherHoldLesson(templateSchedule, startDate, endDate);
    }
}
