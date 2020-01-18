package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.PupilDto;
import com.mper.smartschool.entity.Pupil;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PupilMapper {

    Pupil toEntity(PupilDto dto);

    PupilDto toDto(Pupil entity);
}
