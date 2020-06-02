package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.dto.mapper.ScheduleMapper;
import com.mper.smartschool.dto.mapper.ScheduleMapperImpl;
import com.mper.smartschool.entity.Schedule;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.ScheduleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceImplTest {

    @Mock
    private ScheduleRepo scheduleRepo;

    private final ScheduleMapper scheduleMapper = new ScheduleMapperImpl();

    private ScheduleServiceImpl scheduleService;

    private ScheduleDto scheduleDto;

    @BeforeEach
    public void setUp() {
        scheduleService = new ScheduleServiceImpl(scheduleRepo, scheduleMapper);
        scheduleDto = DtoDirector.makeTestScheduleDtoById(1L);
    }

    @Test
    public void create_success() {
        scheduleDto.setId(null);
        var schedule = scheduleMapper.toEntity(scheduleDto);
        Mockito.when(scheduleRepo.save(schedule)).thenAnswer(invocationOnMock -> {
            Schedule returnedSchedule = invocationOnMock.getArgument(0);
            returnedSchedule.setId(1L);
            return returnedSchedule;
        });

        var result = scheduleService.create(scheduleDto);

        assertNotNull(result.getId());

        assertThat(result).isEqualToIgnoringGivenFields(scheduleDto, "id");
    }

    @Test
    public void update_success() {
        var schedule = scheduleMapper.toEntity(scheduleDto);
        Mockito.when(scheduleRepo.findById(scheduleDto.getId())).thenReturn(Optional.of(schedule));

        Mockito.when(scheduleRepo.save(schedule)).thenReturn(schedule);

        var result = scheduleService.update(scheduleDto);

        assertEquals(result, scheduleDto);
    }

    @Test
    public void update_throwNotFoundException_ifScheduleNotFound() {
        scheduleDto.setId(Long.MAX_VALUE);
        Mockito.when(scheduleRepo.findById(scheduleDto.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> scheduleService.update(scheduleDto));
    }

    @Test
    public void findAll_success() {
        var schedulesDto = getCollectionOfSchedulesDto();
        Mockito.when(scheduleRepo.findAll())
                .thenReturn(schedulesDto.stream().map(scheduleMapper::toEntity).collect(Collectors.toList()));

        var result = scheduleService.findAll();

        assertEquals(result, schedulesDto);
    }

    @Test
    public void findById_success() {
        var schedule = scheduleMapper.toEntity(scheduleDto);
        Mockito.when(scheduleRepo.findById(scheduleDto.getId())).thenReturn(Optional.of(schedule));

        var result = scheduleService.findById(scheduleDto.getId());

        assertEquals(result, scheduleDto);
    }

    @Test
    public void findById_throwNotFoundException_ifScheduleNotFound() {
        Mockito.when(scheduleRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> scheduleService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        var schedule = scheduleMapper.toEntity(scheduleDto);
        Mockito.when(scheduleRepo.findById(scheduleDto.getId())).thenReturn(Optional.of(schedule));
        Mockito.doNothing().when(scheduleRepo).deleteById(scheduleDto.getId());
        assertDoesNotThrow(() -> scheduleService.deleteById(scheduleDto.getId()));
    }

    @Test
    public void deleteById_throwNotFoundException_ifScheduleNotFound() {
        Mockito.when(scheduleRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> scheduleService.deleteById(Long.MAX_VALUE));
    }

    private Collection<ScheduleDto> getCollectionOfSchedulesDto() {
        var scheduleDto2 = DtoDirector.makeTestScheduleDtoById(2L);
        var scheduleDto3 = DtoDirector.makeTestScheduleDtoById(3L);
        return Arrays.asList(scheduleDto, scheduleDto2, scheduleDto3);
    }
}
