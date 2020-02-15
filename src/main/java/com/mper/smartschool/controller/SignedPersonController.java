package com.mper.smartschool.controller;

import com.mper.smartschool.dto.SignedPersonDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.service.SignedPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/signedPersons")
public class SignedPersonController {

    private final SignedPersonService signedPersonService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SignedPersonDto create(@Validated(OnCreate.class) @RequestBody SignedPersonDto signedPersonDto) {
        return signedPersonService.create(signedPersonDto);
    }

    @PutMapping("/{id}")
    public SignedPersonDto update(@PathVariable Long id,
                                  @Validated(OnUpdate.class) @RequestBody SignedPersonDto signedPersonDto) {
        signedPersonDto.setId(id);
        return signedPersonService.update(signedPersonDto);
    }

    @GetMapping
    public Collection<SignedPersonDto> findAll() {
        return signedPersonService.findAll();
    }

    @GetMapping("/{id}")
    public SignedPersonDto findById(@PathVariable Long id) {
        return signedPersonService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        signedPersonService.deleteById(id);
    }
}
