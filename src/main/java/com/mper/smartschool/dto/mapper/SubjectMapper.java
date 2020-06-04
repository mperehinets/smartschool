package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.SubjectDto;
import com.mper.smartschool.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    @Mapping(target = "schoolClassInterval",
            expression = "java(dto.getStartClassInterval() + \"-\" + dto.getEndClassInterval())")
    Subject toEntity(SubjectDto dto);

    @Mapping(target = "startClassInterval",
            expression = "java(Integer.parseInt(entity.getSchoolClassInterval().split(\"-\")[0]))")
    @Mapping(target = "endClassInterval",
            expression = "java(Integer.parseInt(entity.getSchoolClassInterval().split(\"-\")[1]))")
    SubjectDto toDto(Subject entity);
}
