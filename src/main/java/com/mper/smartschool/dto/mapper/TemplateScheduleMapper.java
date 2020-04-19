package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.entity.TemplateSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TemplateScheduleMapper {

    @Mapping(target = "subject", source = "dto.teachersSubject.subject")
    TemplateSchedule toEntity(TemplateScheduleDto dto);

    @Mapping(target = "teachersSubject.subject", source = "entity.subject")
    TemplateScheduleDto toDto(TemplateSchedule entity);
}
