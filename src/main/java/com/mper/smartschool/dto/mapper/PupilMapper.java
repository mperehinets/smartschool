package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.PupilDto;
import com.mper.smartschool.model.Pupil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PupilMapper {

    private final ModelMapper mapper;

    @Autowired
    public PupilMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Pupil toEntity(PupilDto dto) {
        return mapper.map(dto, Pupil.class);
    }

    public PupilDto toDto(Pupil entity) {
        return mapper.map(entity, PupilDto.class);
    }
}
