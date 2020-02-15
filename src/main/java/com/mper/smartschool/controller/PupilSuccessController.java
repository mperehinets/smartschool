package com.mper.smartschool.controller;

import com.mper.smartschool.dto.PupilSuccessDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.service.PupilSuccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/pupilSuccesses")
public class PupilSuccessController {

    private final PupilSuccessService pupilSuccessService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PupilSuccessDto create(@Validated(OnCreate.class) @RequestBody PupilSuccessDto pupilSuccessDto) {
        return pupilSuccessService.create(pupilSuccessDto);
    }

    @PutMapping("/{id}")
    public PupilSuccessDto update(@PathVariable Long id,
                                  @Validated(OnUpdate.class) @RequestBody PupilSuccessDto pupilSuccessDto) {
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
