package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.PupilDto;
import com.mper.smartschool.entity.Pupil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PupilMapper {

    Pupil toEntity(PupilDto dto);

    @Mapping(target = "schoolClass.classTeacher", ignore = true)
    PupilDto toDto(Pupil entity);
}
