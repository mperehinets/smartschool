package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.TeachersSubjectDto;
import com.mper.smartschool.dto.mapper.TeachersSubjectMapper;
import com.mper.smartschool.dto.mapper.TeachersSubjectMapperImpl;
import com.mper.smartschool.model.TeachersSubject;
import com.mper.smartschool.repository.TeachersSubjectRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TeachersSubjectServiceImplTest {

    @Mock
    private TeachersSubjectRepo teachersSubjectRepo;

    private TeachersSubjectMapper teachersSubjectMapper = new TeachersSubjectMapperImpl();

    private TeachersSubjectServiceImpl teachersSubjectService;

    private TeachersSubjectDto teachersSubjectDto;

    @BeforeEach
    public void setUp() {
        teachersSubjectService = new TeachersSubjectServiceImpl(teachersSubjectRepo, teachersSubjectMapper);
        teachersSubjectDto = DtoDirector.makeTestTeachersSubjectDtoById(1L);
    }

    @Test
    public void create_success() {
        teachersSubjectDto.setId(null);
        teachersSubjectDto.setStartDate(null);
        TeachersSubject teachersSubject = teachersSubjectMapper.toEntity(teachersSubjectDto);
        LocalDate startDate = LocalDate.now();
        teachersSubject.setStartDate(startDate);
        Mockito.when(teachersSubjectRepo.save(teachersSubject)).thenAnswer(invocationOnMock -> {
            TeachersSubject returnedTeachersSubject = invocationOnMock.getArgument(0);
            returnedTeachersSubject.setId(1L);
            return returnedTeachersSubject;
        });

        TeachersSubjectDto result = teachersSubjectService.create(teachersSubjectDto);

        assertNotNull(result.getId());

        assertEquals(result.getStartDate(), startDate);

        assertNull(result.getEndDate());

        assertThat(result).isEqualToIgnoringGivenFields(teachersSubjectDto,
                "id", "startDate", "endDate");
    }

    @Test
    public void update_success() {
        TeachersSubject teachersSubject = teachersSubjectMapper.toEntity(teachersSubjectDto);
        Mockito.when(teachersSubjectRepo.findById(teachersSubjectDto.getId())).thenReturn(Optional.of(teachersSubject));

        Mockito.when(teachersSubjectRepo.save(teachersSubject)).thenReturn(teachersSubject);

        TeachersSubjectDto result = teachersSubjectService.update(teachersSubjectDto);

        assertEquals(result, teachersSubjectDto);
    }

    @Test
    public void update_throwEntityNotFoundException_ifTeachersSubjectNotFound() {
        teachersSubjectDto.setId(Long.MAX_VALUE);
        Mockito.when(teachersSubjectRepo.findById(teachersSubjectDto.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> teachersSubjectService.update(teachersSubjectDto));
    }

    @Test
    public void findAll_success() {
        Collection<TeachersSubjectDto> teachersSubjectsDto = getCollectionOfTeachersSubjectsDto();
        Mockito.when(teachersSubjectRepo.findAll())
                .thenReturn(teachersSubjectsDto.stream()
                        .map(teachersSubjectMapper::toEntity).collect(Collectors.toList()));

        Collection<TeachersSubjectDto> result = teachersSubjectService.findAll();

        assertEquals(result, teachersSubjectsDto);
    }

    @Test
    public void findById_success() {
        TeachersSubject teachersSubject = teachersSubjectMapper.toEntity(teachersSubjectDto);
        Mockito.when(teachersSubjectRepo.findById(teachersSubjectDto.getId())).thenReturn(Optional.of(teachersSubject));

        TeachersSubjectDto result = teachersSubjectService.findById(teachersSubjectDto.getId());

        assertEquals(result, teachersSubjectDto);
    }

    @Test
    public void findById_throwEntityNotFoundException_ifTeachersSubjectNotFound() {
        Mockito.when(teachersSubjectRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> teachersSubjectService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        TeachersSubject teachersSubject = teachersSubjectMapper.toEntity(teachersSubjectDto);
        Mockito.when(teachersSubjectRepo.findById(teachersSubjectDto.getId())).thenReturn(Optional.of(teachersSubject));
        Mockito.doNothing().when(teachersSubjectRepo).deleteById(teachersSubjectDto.getId());
        assertDoesNotThrow(() -> teachersSubjectService.deleteById(teachersSubjectDto.getId()));
    }

    @Test
    public void deleteById_throwEntityNotFoundException_ifTeachersSubjectNotFound() {
        Mockito.when(teachersSubjectRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> teachersSubjectService.deleteById(Long.MAX_VALUE));
    }

    @Test
    public void stopTeachSubjectById_success() {
        TeachersSubject foundTeachersSubject = teachersSubjectMapper.toEntity(teachersSubjectDto);
        Mockito.when(teachersSubjectRepo.findById(teachersSubjectDto.getId()))
                .thenReturn(Optional.of(foundTeachersSubject));

        TeachersSubject teachersSubject = teachersSubjectMapper.toEntity(teachersSubjectDto);
        LocalDate endDate = LocalDate.now();
        teachersSubject.setEndDate(endDate);
        Mockito.when(teachersSubjectRepo.save(teachersSubject)).thenReturn(teachersSubject);

        TeachersSubjectDto result = teachersSubjectService.stopTeachSubjectById(teachersSubjectDto.getId());

        assertEquals(result.getEndDate(), endDate);

        assertThat(result).isEqualToIgnoringGivenFields(teachersSubjectDto,
                "endDate");
    }

    @Test
    public void stopTeachSubjectById_notGenerateNewEndDate_ifFoundTeachersSubjectAlreadyStopped() {
        TeachersSubject foundTeachersSubject = teachersSubjectMapper.toEntity(teachersSubjectDto);
        LocalDate endDateOfFoundTeachersSubject = LocalDate.now().minusMonths(3);
        foundTeachersSubject.setEndDate(endDateOfFoundTeachersSubject);
        Mockito.when(teachersSubjectRepo.findById(teachersSubjectDto.getId()))
                .thenReturn(Optional.of(foundTeachersSubject));

        Mockito.when(teachersSubjectRepo.save(foundTeachersSubject)).thenReturn(foundTeachersSubject);

        TeachersSubjectDto result = teachersSubjectService.stopTeachSubjectById(teachersSubjectDto.getId());

        assertEquals(result.getEndDate(), endDateOfFoundTeachersSubject);

        assertThat(result).isEqualToIgnoringGivenFields(teachersSubjectDto,
                "endDate");
    }

    @Test
    public void stopTeachSubjectById_throwEntityNotFoundException_ifTeachersSubjectNotFound() {
        Mockito.when(teachersSubjectRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> teachersSubjectService.stopTeachSubjectById(Long.MAX_VALUE));
    }


    private Collection<TeachersSubjectDto> getCollectionOfTeachersSubjectsDto() {
        TeachersSubjectDto teachersSubjectDto2 = DtoDirector.makeTestTeachersSubjectDtoById(2L);
        TeachersSubjectDto teachersSubjectDto3 = DtoDirector.makeTestTeachersSubjectDtoById(3L);
        return Arrays.asList(teachersSubjectDto, teachersSubjectDto2, teachersSubjectDto3);
    }
}
