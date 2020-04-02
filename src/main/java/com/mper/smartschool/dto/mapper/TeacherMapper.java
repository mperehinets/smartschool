package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.TeacherDto;
import com.mper.smartschool.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    Teacher toEntity(TeacherDto dto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    TeacherDto toDto(Teacher entity);
}
