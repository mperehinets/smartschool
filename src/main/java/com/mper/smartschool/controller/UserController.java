package com.mper.smartschool.controller;

import com.mper.smartschool.dto.ResetPasswordDto;
import com.mper.smartschool.dto.UpdateAvatarDto;
import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smartschool/users")
public class UserController {

    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        userDto.setId(id);
        return userService.update(userDto);
    }

    @GetMapping
    public Collection<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PutMapping("/activate/{id}")
    public void activateById(@PathVariable Long id) {
        userService.activateById(id);
    }

    @PutMapping("/deactivate/{id}")
    public void deactivateById(@PathVariable Long id) {
        userService.deactivateById(id);
    }

    @GetMapping("/")
    public UserDto findByEmail(@RequestParam("email") String email) {
        return userService.findByEmail(email);
    }

    @PutMapping("/give-admin/{id}")
    public UserDto makeAdminById(@PathVariable Long id) {
        return userService.giveAdminById(id);
    }

    @PutMapping("/take-admin-away/{id}")
    public UserDto takeAdminAwayById(@PathVariable Long id) {
        return userService.takeAdminAwayById(id);
    }

    @PutMapping("/reset-password-by-admin/{id}")
    public void resetPasswordByAdmin(@PathVariable Long id, @Validated @RequestBody ResetPasswordDto resetPasswordDto) {
        resetPasswordDto.setId(id);
        userService.resetPasswordByAdmin(resetPasswordDto);
    }

    //Following methods without tests
    @PutMapping("/update-avatar/{id}")
    public void updateAvatarById(@PathVariable Long id, @RequestBody UpdateAvatarDto updateAvatarDto) {
        updateAvatarDto.setId(id);
        userService.updateAvatarById(updateAvatarDto);
    }

    @GetMapping("/count")
    public Long getCount() {
        return userService.getCount();
    }
}
