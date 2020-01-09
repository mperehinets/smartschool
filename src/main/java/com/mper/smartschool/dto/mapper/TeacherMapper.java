package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.TeacherDto;
import com.mper.smartschool.model.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    private final ModelMapper mapper;

    @Autowired
    public TeacherMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Teacher toEntity(TeacherDto dto) {
        return mapper.map(dto, Teacher.class);
    }

    public TeacherDto toDto(Teacher entity) {
        return mapper.map(entity, TeacherDto.class);
    }
}
