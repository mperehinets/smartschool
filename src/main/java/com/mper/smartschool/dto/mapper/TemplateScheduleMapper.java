package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.model.TemplateSchedule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemplateScheduleMapper {

    private final ModelMapper mapper;

    @Autowired
    public TemplateScheduleMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public TemplateSchedule toEntity(TemplateScheduleDto dto) {
        return mapper.map(dto, TemplateSchedule.class);
    }

    public TemplateScheduleDto toDto(TemplateSchedule entity) {
        return mapper.map(entity, TemplateScheduleDto.class);
    }
}
