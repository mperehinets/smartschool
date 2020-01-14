package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.TeachersSubjectDto;
import com.mper.smartschool.model.TeachersSubject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeachersSubjectMapper {

    TeachersSubject toEntity(TeachersSubjectDto dto);

    TeachersSubjectDto toDto(TeachersSubject entity);
}
