package com.mper.smartschool.controller;

import com.mper.smartschool.dto.ChangeStatusDto;
import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.dto.transfer.OnCreate;
import com.mper.smartschool.dto.transfer.OnUpdate;
import com.mper.smartschool.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @PutMapping("/change-status/{id}")
    public void changeStatusById(@PathVariable Long id, @RequestBody ChangeStatusDto changeStatusDto) {
        changeStatusDto.setId(id);
        userService.changeStatusById(changeStatusDto);
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

    //Following methods without tests
    @PutMapping("/current/update-avatar")
    public void updateAvatarForCurrent(@RequestBody String avatarName) {
        userService.updateAvatarForCurrent(avatarName);
    }

    @GetMapping("/count")
    public Long getCount() {
        return userService.getCount();
    }

    @GetMapping("/current")
    public UserDto findCurrent() {
        return userService.findCurrent();
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(HttpServletRequest req) {

    }
}
