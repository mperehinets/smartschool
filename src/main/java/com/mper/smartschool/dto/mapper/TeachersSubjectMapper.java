package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.TeachersSubjectDto;
import com.mper.smartschool.model.TeachersSubject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeachersSubjectMapper {

    private final ModelMapper mapper;

    @Autowired
    public TeachersSubjectMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public TeachersSubject toEntity(TeachersSubjectDto dto) {
        return mapper.map(dto, TeachersSubject.class);
    }

    public TeachersSubjectDto toDto(TeachersSubject entity) {
        return mapper.map(entity, TeachersSubjectDto.class);
    }
}
