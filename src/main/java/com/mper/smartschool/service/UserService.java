package com.mper.smartschool.service;

import com.mper.smartschool.dto.ChangeStatusDto;
import com.mper.smartschool.dto.ResetPasswordDto;
import com.mper.smartschool.dto.UserDto;

import java.util.Collection;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    Collection<UserDto> findAll();

    UserDto findById(Long id);

    void changeStatusById(ChangeStatusDto changeStatusDto);

    UserDto giveAdminById(Long id);

    UserDto takeAdminAwayById(Long id);

    UserDto findByEmail(String email);

    void resetPassword(ResetPasswordDto resetPasswordDto);

    //Following methods without tests
    void updateAvatarForCurrent(String avatarName);

    Long getCount();

    UserDto findCurrent();
}
