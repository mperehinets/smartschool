package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.dto.mapper.ScheduleMapper;
import com.mper.smartschool.dto.mapper.ScheduleMapperImpl;
import com.mper.smartschool.model.Schedule;
import com.mper.smartschool.repository.ScheduleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
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

    private ScheduleMapper scheduleMapper = new ScheduleMapperImpl();

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
        Schedule schedule = scheduleMapper.toEntity(scheduleDto);
        Mockito.when(scheduleRepo.save(schedule)).thenAnswer(invocationOnMock -> {
            Schedule returnedSchedule = invocationOnMock.getArgument(0);
            returnedSchedule.setId(1L);
            return returnedSchedule;
        });

        ScheduleDto result = scheduleService.create(scheduleDto);

        assertNotNull(result.getId());

        assertThat(result).isEqualToIgnoringGivenFields(scheduleDto, "id");
    }

    @Test
    public void update_success() {
        Schedule schedule = scheduleMapper.toEntity(scheduleDto);
        Mockito.when(scheduleRepo.findById(scheduleDto.getId())).thenReturn(Optional.of(schedule));

        Mockito.when(scheduleRepo.save(schedule)).thenReturn(schedule);

        ScheduleDto result = scheduleService.update(scheduleDto);

        assertEquals(result, scheduleDto);
    }

    @Test
    public void update_throwEntityNotFoundException_ifScheduleNotFound() {
        scheduleDto.setId(Long.MAX_VALUE);
        Mockito.when(scheduleRepo.findById(scheduleDto.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> scheduleService.update(scheduleDto));
    }

    @Test
    public void findAll_success() {
        Collection<ScheduleDto> schedulesDto = getCollectionOfSchedulesDto();
        Mockito.when(scheduleRepo.findAll())
                .thenReturn(schedulesDto.stream().map(scheduleMapper::toEntity).collect(Collectors.toList()));

        Collection<ScheduleDto> result = scheduleService.findAll();

        assertEquals(result, schedulesDto);
    }

    @Test
    public void findById_success() {
        Schedule schedule = scheduleMapper.toEntity(scheduleDto);
        Mockito.when(scheduleRepo.findById(scheduleDto.getId())).thenReturn(Optional.of(schedule));

        ScheduleDto result = scheduleService.findById(scheduleDto.getId());

        assertEquals(result, scheduleDto);
    }

    @Test
    public void findById_throwEntityNotFoundException_ifScheduleNotFound() {
        Mockito.when(scheduleRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> scheduleService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        Schedule schedule = scheduleMapper.toEntity(scheduleDto);
        Mockito.when(scheduleRepo.findById(scheduleDto.getId())).thenReturn(Optional.of(schedule));
        Mockito.doNothing().when(scheduleRepo).deleteById(scheduleDto.getId());
        assertDoesNotThrow(() -> scheduleService.deleteById(scheduleDto.getId()));
    }

    @Test
    public void deleteById_throwEntityNotFoundException_ifScheduleNotFound() {
        Mockito.when(scheduleRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> scheduleService.deleteById(Long.MAX_VALUE));
    }

    private Collection<ScheduleDto> getCollectionOfSchedulesDto() {
        ScheduleDto scheduleDto2 = DtoDirector.makeTestScheduleDtoById(2L);
        ScheduleDto scheduleDto3 = DtoDirector.makeTestScheduleDtoById(3L);
        return Arrays.asList(scheduleDto, scheduleDto2, scheduleDto3);
    }
}
