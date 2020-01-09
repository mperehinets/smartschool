package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.HomeworkDto;
import com.mper.smartschool.model.Homework;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeworkMapper {

    private final ModelMapper mapper;

    @Autowired
    public HomeworkMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Homework toEntity(HomeworkDto dto) {
        return mapper.map(dto, Homework.class);
    }

    public HomeworkDto toDto(Homework entity) {
        return mapper.map(entity, HomeworkDto.class);
    }
}
