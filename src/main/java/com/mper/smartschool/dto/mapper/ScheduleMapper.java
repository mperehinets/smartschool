package com.mper.smartschool.dto.mapper;

import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    Schedule toEntity(ScheduleDto dto);

    @Mapping(target = "teachersSubject.teacher.roles", ignore = true)
    @Mapping(target = "teachersSubject.teacher.password", ignore = true)
    @Mapping(target = "schoolClass.classTeacher.roles", ignore = true)
    @Mapping(target = "schoolClass.classTeacher.password", ignore = true)
    ScheduleDto toDto(Schedule entity);
}
