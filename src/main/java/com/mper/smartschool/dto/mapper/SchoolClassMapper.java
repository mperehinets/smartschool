package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.entity.SchoolClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolClassMapper {

    SchoolClass toEntity(SchoolClassDto dto);

    SchoolClassDto toDto(SchoolClass entity);
}
