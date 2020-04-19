package com.mper.smartschool.dto;

import com.mper.smartschool.dto.transfer.OnGenerateSchedule;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;

@Data
public class GenerateScheduleDto {
    @NotNull(groups = OnGenerateSchedule.class,
            message = "{generateScheduleDto.schoolClass.notnull}")
    private SchoolClassDto schoolClass;

    @NotNull(groups = OnGenerateSchedule.class,
            message = "{generateScheduleDto.startDate.notnull}")
    private LocalDate startDate;

    @NotNull(groups = OnGenerateSchedule.class,
            message = "{generateScheduleDto.endDate.notnull}")
    private LocalDate endDate;

    @NotNull(groups = OnGenerateSchedule.class,
            message = "{generateScheduleDto.templatesSchedule.notnull}")
    private Collection<@Valid TemplateScheduleDto> templatesSchedule;
}
