package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.dto.mapper.TemplateScheduleMapper;
import com.mper.smartschool.dto.mapper.TemplateScheduleMapperImpl;
import com.mper.smartschool.entity.TemplateSchedule;
import com.mper.smartschool.repository.TemplateScheduleRepo;
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
public class TemplateScheduleServiceImplTest {

    @Mock
    private TemplateScheduleRepo templateScheduleRepo;

    private TemplateScheduleMapper templateScheduleMapper = new TemplateScheduleMapperImpl();

    private TemplateScheduleServiceImpl templateScheduleService;

    private TemplateScheduleDto templateScheduleDto;

    @BeforeEach
    public void setUp() {
        templateScheduleService = new TemplateScheduleServiceImpl(templateScheduleRepo, templateScheduleMapper);
        templateScheduleDto = DtoDirector.makeTestTemplateScheduleDtoById(1L);
    }

    @Test
    public void create_success() {
        templateScheduleDto.setId(null);
        TemplateSchedule templateSchedule = templateScheduleMapper.toEntity(templateScheduleDto);
        Mockito.when(templateScheduleRepo.save(templateSchedule)).thenAnswer(invocationOnMock -> {
            TemplateSchedule returnedTemplateSchedule = invocationOnMock.getArgument(0);
            returnedTemplateSchedule.setId(1L);
            return returnedTemplateSchedule;
        });

        TemplateScheduleDto result = templateScheduleService.create(templateScheduleDto);

        assertNotNull(result.getId());

        assertThat(result).isEqualToIgnoringGivenFields(templateScheduleDto, "id");
    }

    @Test
    public void update_success() {
        TemplateSchedule templateSchedule = templateScheduleMapper.toEntity(templateScheduleDto);
        Mockito.when(templateScheduleRepo.findById(templateScheduleDto.getId()))
                .thenReturn(Optional.of(templateSchedule));

        Mockito.when(templateScheduleRepo.save(templateSchedule)).thenReturn(templateSchedule);

        TemplateScheduleDto result = templateScheduleService.update(templateScheduleDto);

        assertEquals(result, templateScheduleDto);
    }

    @Test
    public void update_throwEntityNotFoundException_ifTemplateScheduleNotFound() {
        templateScheduleDto.setId(Long.MAX_VALUE);
        Mockito.when(templateScheduleRepo.findById(templateScheduleDto.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> templateScheduleService.update(templateScheduleDto));
    }

    @Test
    public void findAll_success() {
        Collection<TemplateScheduleDto> templateSchedulesDto = getCollectionOfTemplateSchedulesDto();
        Mockito.when(templateScheduleRepo.findAll())
                .thenReturn(templateSchedulesDto.stream()
                        .map(templateScheduleMapper::toEntity).collect(Collectors.toList()));

        Collection<TemplateScheduleDto> result = templateScheduleService.findAll();

        assertEquals(result, templateSchedulesDto);
    }

    @Test
    public void findById_success() {
        TemplateSchedule templateSchedule = templateScheduleMapper.toEntity(templateScheduleDto);
        Mockito.when(templateScheduleRepo.findById(templateScheduleDto.getId()))
                .thenReturn(Optional.of(templateSchedule));

        TemplateScheduleDto result = templateScheduleService.findById(templateScheduleDto.getId());

        assertEquals(result, templateScheduleDto);
    }

    @Test
    public void findById_throwEntityNotFoundException_ifTemplateScheduleNotFound() {
        Mockito.when(templateScheduleRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> templateScheduleService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        TemplateSchedule templateSchedule = templateScheduleMapper.toEntity(templateScheduleDto);
        Mockito.when(templateScheduleRepo.findById(templateScheduleDto.getId()))
                .thenReturn(Optional.of(templateSchedule));
        Mockito.doNothing().when(templateScheduleRepo).deleteById(templateScheduleDto.getId());
        assertDoesNotThrow(() -> templateScheduleService.deleteById(templateScheduleDto.getId()));
    }

    @Test
    public void deleteById_throwEntityNotFoundException_ifTemplateScheduleNotFound() {
        Mockito.when(templateScheduleRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> templateScheduleService.deleteById(Long.MAX_VALUE));
    }

    private Collection<TemplateScheduleDto> getCollectionOfTemplateSchedulesDto() {
        TemplateScheduleDto templateScheduleDto2 = DtoDirector.makeTestTemplateScheduleDtoById(2L);
        TemplateScheduleDto templateScheduleDto3 = DtoDirector.makeTestTemplateScheduleDtoById(3L);
        return Arrays.asList(templateScheduleDto, templateScheduleDto2, templateScheduleDto3);
    }
}
