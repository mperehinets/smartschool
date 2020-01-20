package com.mper.smartschool.controller;

import com.mper.smartschool.dto.PupilDto;
import com.mper.smartschool.service.PupilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/pupils")
public class PupilController {

    private final PupilService pupilService;

    @Autowired
    public PupilController(PupilService pupilService) {
        this.pupilService = pupilService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PupilDto create(@RequestBody PupilDto pupilDto) {
        return pupilService.create(pupilDto);
    }

    @PutMapping("/{id}")
    public PupilDto update(@PathVariable Long id, @RequestBody PupilDto pupilDto) {
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
