package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.SignedPersonDto;
import com.mper.smartschool.model.SignedPerson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SignedPersonMapper {

    SignedPerson toEntity(SignedPersonDto dto);

    SignedPersonDto toDto(SignedPerson entity);
}
