package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.entity.SchoolClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SchoolClassMapper {

    SchoolClass toEntity(SchoolClassDto dto);

    @Mapping(target = "classTeacher.roles", ignore = true)
    @Mapping(target = "classTeacher.password", ignore = true)
    SchoolClassDto toDto(SchoolClass entity);
}
