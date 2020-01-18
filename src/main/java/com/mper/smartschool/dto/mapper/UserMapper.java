package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User entity);
}
