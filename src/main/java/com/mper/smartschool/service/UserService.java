package com.mper.smartschool.service;

import com.mper.smartschool.dto.ResetPasswordDto;
import com.mper.smartschool.dto.UpdateAvatarDto;
import com.mper.smartschool.dto.UserDto;

import java.util.Collection;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    Collection<UserDto> findAll();

    UserDto findById(Long id);

    void deleteById(Long id);

    void activateById(Long id);

    void deactivateById(Long id);

    UserDto giveAdminById(Long id);

    UserDto takeAdminAwayById(Long id);

    UserDto findByEmail(String email);

    void resetPasswordByAdmin(ResetPasswordDto resetPasswordDto);

    //Following methods without tests
    void updateAvatarById(UpdateAvatarDto updateAvatarDto);
}
