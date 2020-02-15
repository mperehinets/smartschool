package com.mper.smartschool.controller;

import com.mper.smartschool.dto.PupilDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.service.PupilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/pupils")
public class PupilController {

    private final PupilService pupilService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PupilDto create(@Validated(OnCreate.class) @RequestBody PupilDto pupilDto) {
        return pupilService.create(pupilDto);
    }

    @PutMapping("/{id}")
    public PupilDto update(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody PupilDto pupilDto) {
        pupilDto.setId(id);
        return pupilService.update(pupilDto);
    }

    @GetMapping
    public Collection<PupilDto> findAll() {
        return pupilService.findAll();
    }

    @GetMapping("/{id}")
    public PupilDto findById(@PathVariable Long id) {
        return pupilService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        pupilService.deleteById(id);
    }
}
