package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.SignedPersonDto;
import com.mper.smartschool.model.SignedPerson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SignedPersonMapper {

    private final ModelMapper mapper;

    @Autowired
    public SignedPersonMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public SignedPerson toEntity(SignedPersonDto dto) {
        return mapper.map(dto, SignedPerson.class);
    }

    public SignedPersonDto toDto(SignedPerson entity) {
        return mapper.map(entity, SignedPersonDto.class);
    }
}
