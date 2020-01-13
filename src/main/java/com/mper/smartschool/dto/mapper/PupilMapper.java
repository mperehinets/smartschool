package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.PupilDto;
import com.mper.smartschool.model.Pupil;
import org.mapstruct.Mapper;

@Mapper
public interface PupilMapper {

    Pupil toEntity(PupilDto dto);

    PupilDto toDto(Pupil entity);
}
