package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.SubjectDto;
import com.mper.smartschool.model.Subject;
import org.mapstruct.Mapper;

@Mapper
public interface SubjectMapper {

    Subject toEntity(SubjectDto dto);

    SubjectDto toDto(Subject entity);
}
