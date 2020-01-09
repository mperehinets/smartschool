package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.model.SchoolClass;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchoolClassMapper {

    private final ModelMapper mapper;

    @Autowired
    public SchoolClassMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public SchoolClass toEntity(SchoolClassDto dto) {
        return mapper.map(dto, SchoolClass.class);
    }

    public SchoolClassDto toDto(SchoolClass entity) {
        return mapper.map(entity, SchoolClassDto.class);
    }
}
