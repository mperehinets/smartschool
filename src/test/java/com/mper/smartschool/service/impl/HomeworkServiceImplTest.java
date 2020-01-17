package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.HomeworkDto;
import com.mper.smartschool.dto.mapper.HomeworkMapper;
import com.mper.smartschool.dto.mapper.HomeworkMapperImpl;
import com.mper.smartschool.model.Homework;
import com.mper.smartschool.repository.HomeworkRepo;
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
public class HomeworkServiceImplTest {

    @Mock
    private HomeworkRepo homeworkRepo;

    private HomeworkMapper homeworkMapper = new HomeworkMapperImpl();

    private HomeworkServiceImpl homeworkService;

    private HomeworkDto homeworkDto;

    @BeforeEach
    public void setUp() {
        homeworkService = new HomeworkServiceImpl(homeworkRepo, homeworkMapper);
        homeworkDto = DtoDirector.makeTestHomeworkDtoById(1L);
    }

    @Test
    public void create_success() {
        homeworkDto.setId(null);
        Homework homework = homeworkMapper.toEntity(homeworkDto);
        Mockito.when(homeworkRepo.save(homework)).thenAnswer(invocationOnMock -> {
            Homework returnedHomework = invocationOnMock.getArgument(0);
            returnedHomework.setId(1L);
            return returnedHomework;
        });

        HomeworkDto result = homeworkService.create(homeworkDto);

        assertNotNull(result.getId());

        assertThat(result).isEqualToIgnoringGivenFields(homeworkDto, "id");
    }

    @Test
    public void update_success() {
        Homework homework = homeworkMapper.toEntity(homeworkDto);
        Mockito.when(homeworkRepo.findById(homeworkDto.getId())).thenReturn(Optional.of(homework));

        Mockito.when(homeworkRepo.save(homework)).thenReturn(homework);

        HomeworkDto result = homeworkService.update(homeworkDto);

        assertEquals(result, homeworkDto);
    }

    @Test
    public void update_throwEntityNotFoundException_ifHomeworkNotFound() {
        homeworkDto.setId(Long.MAX_VALUE);
        Mockito.when(homeworkRepo.findById(homeworkDto.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> homeworkService.update(homeworkDto));
    }

    @Test
    public void findAll_success() {
        Collection<HomeworkDto> homeworksDto = getCollectionOfHomeworksDto();
        Mockito.when(homeworkRepo.findAll())
                .thenReturn(homeworksDto.stream().map(homeworkMapper::toEntity).collect(Collectors.toList()));

        Collection<HomeworkDto> result = homeworkService.findAll();

        assertEquals(result, homeworksDto);
    }

    @Test
    public void findById_success() {
        Homework homework = homeworkMapper.toEntity(homeworkDto);
        Mockito.when(homeworkRepo.findById(homeworkDto.getId())).thenReturn(Optional.of(homework));

        HomeworkDto result = homeworkService.findById(homeworkDto.getId());

        assertEquals(result, homeworkDto);
    }

    @Test
    public void findById_throwEntityNotFoundException_ifHomeworkNotFound() {
        Mockito.when(homeworkRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> homeworkService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        Homework homework = homeworkMapper.toEntity(homeworkDto);
        Mockito.when(homeworkRepo.findById(homeworkDto.getId())).thenReturn(Optional.of(homework));
        Mockito.doNothing().when(homeworkRepo).deleteById(homeworkDto.getId());
        assertDoesNotThrow(() -> homeworkService.deleteById(homeworkDto.getId()));
    }

    @Test
    public void deleteById_throwEntityNotFoundException_ifHomeworkNotFound() {
        Mockito.when(homeworkRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> homeworkService.deleteById(Long.MAX_VALUE));
    }

    private Collection<HomeworkDto> getCollectionOfHomeworksDto() {
        HomeworkDto homeworkDto2 = DtoDirector.makeTestHomeworkDtoById(2L);
        HomeworkDto homeworkDto3 = DtoDirector.makeTestHomeworkDtoById(3L);
        return Arrays.asList(homeworkDto, homeworkDto2, homeworkDto3);
    }
}
