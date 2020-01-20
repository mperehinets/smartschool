package com.mper.smartschool.controller;

import com.mper.smartschool.dto.SignedPersonDto;
import com.mper.smartschool.service.SignedPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/signedPersons")
public class SignedPersonController {

    private final SignedPersonService signedPersonService;

    @Autowired
    public SignedPersonController(SignedPersonService signedPersonService) {
        this.signedPersonService = signedPersonService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SignedPersonDto create(@RequestBody SignedPersonDto signedPersonDto) {
        return signedPersonService.create(signedPersonDto);
    }

    @PutMapping("/{id}")
    public SignedPersonDto update(@PathVariable Long id, @RequestBody SignedPersonDto signedPersonDto) {
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
