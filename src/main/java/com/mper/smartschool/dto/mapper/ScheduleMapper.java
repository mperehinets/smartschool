package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.model.Schedule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    private final ModelMapper mapper;

    @Autowired
    public ScheduleMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Schedule toEntity(ScheduleDto dto) {
        return mapper.map(dto, Schedule.class);
    }

    public ScheduleDto toDto(Schedule entity) {
        return mapper.map(entity, ScheduleDto.class);
    }
}
