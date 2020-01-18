package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.entity.TemplateSchedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateScheduleMapper {

    TemplateSchedule toEntity(TemplateScheduleDto dto);

    TemplateScheduleDto toDto(TemplateSchedule entity);
}
