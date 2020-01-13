package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.TeachersSubjectDto;
import com.mper.smartschool.model.TeachersSubject;
import org.mapstruct.Mapper;

@Mapper
public interface TeachersSubjectMapper {

    TeachersSubject toEntity(TeachersSubjectDto dto);

    TeachersSubjectDto toDto(TeachersSubject entity);
}
