package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.TeachersSubjectDto;
import com.mper.smartschool.entity.TeachersSubject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeachersSubjectMapper {

    TeachersSubject toEntity(TeachersSubjectDto dto);

    @Mapping(target = "teacher.password", ignore = true)
    @Mapping(target = "teacher.roles", ignore = true)
    TeachersSubjectDto toDto(TeachersSubject entity);
}
