package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.SubjectDto;
import com.mper.smartschool.model.Subject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    private final ModelMapper mapper;

    @Autowired
    public SubjectMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Subject toEntity(SubjectDto dto) {
        return mapper.map(dto, Subject.class);
    }

    public SubjectDto toDto(Subject entity) {
        return mapper.map(entity, SubjectDto.class);
    }
}
