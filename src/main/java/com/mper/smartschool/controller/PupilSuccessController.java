package com.mper.smartschool.controller;

import com.mper.smartschool.dto.PupilSuccessDto;
import com.mper.smartschool.service.PupilSuccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/pupilSuccesses")
public class PupilSuccessController {

    private final PupilSuccessService pupilSuccessService;

    @Autowired
    public PupilSuccessController(PupilSuccessService pupilSuccessService) {
        this.pupilSuccessService = pupilSuccessService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PupilSuccessDto create(@RequestBody PupilSuccessDto pupilSuccessDto) {
        return pupilSuccessService.create(pupilSuccessDto);
    }

    @PostMapping("/{id}")
    public PupilSuccessDto update(@PathVariable Long id, @RequestBody PupilSuccessDto pupilSuccessDto) {
        pupilSuccessDto.setId(id);
        return pupilSuccessService.update(pupilSuccessDto);
    }

    @GetMapping
    public Collection<PupilSuccessDto> findAll() {
        return pupilSuccessService.findAll();
    }

    @GetMapping("/{id}")
    public PupilSuccessDto findById(@PathVariable Long id) {
        return pupilSuccessService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        pupilSuccessService.deleteById(id);
    }
}
