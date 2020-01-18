package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.entity.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    Schedule toEntity(ScheduleDto dto);

    ScheduleDto toDto(Schedule entity);
}
