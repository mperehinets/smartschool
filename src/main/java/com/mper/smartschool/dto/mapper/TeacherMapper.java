package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.TeacherDto;
import com.mper.smartschool.model.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    Teacher toEntity(TeacherDto dto);

    TeacherDto toDto(Teacher entity);
}
