package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper mapper;

    @Autowired
    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public User toEntity(UserDto dto) {
        return mapper.map(dto, User.class);
    }

    public UserDto toDto(User entity) {
        return mapper.map(entity, UserDto.class);
    }
}
