package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.PupilSuccessDto;
import com.mper.smartschool.model.PupilSuccess;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PupilSuccessMapper {

    PupilSuccess toEntity(PupilSuccessDto dto);

    PupilSuccessDto toDto(PupilSuccess entity);
}
