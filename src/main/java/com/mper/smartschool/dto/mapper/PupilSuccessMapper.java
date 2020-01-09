package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.PupilSuccessDto;
import com.mper.smartschool.model.PupilSuccess;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PupilSuccessMapper {

    private final ModelMapper mapper;

    @Autowired
    public PupilSuccessMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PupilSuccess toEntity(PupilSuccessDto dto) {
        return mapper.map(dto, PupilSuccess.class);
    }

    public PupilSuccessDto toDto(PupilSuccess entity) {
        return mapper.map(entity, PupilSuccessDto.class);
    }
}
