package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User entity);
}
