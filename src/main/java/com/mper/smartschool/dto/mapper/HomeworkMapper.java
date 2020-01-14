package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.HomeworkDto;
import com.mper.smartschool.model.Homework;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HomeworkMapper {

    Homework toEntity(HomeworkDto dto);

    HomeworkDto toDto(Homework entity);
}
