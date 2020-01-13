package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.model.SchoolClass;
import org.mapstruct.Mapper;

@Mapper
public interface SchoolClassMapper {

    SchoolClass toEntity(SchoolClassDto dto);

    SchoolClassDto toDto(SchoolClass entity);
}
