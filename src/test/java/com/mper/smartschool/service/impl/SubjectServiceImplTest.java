package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.SubjectDto;
import com.mper.smartschool.dto.mapper.SubjectMapper;
import com.mper.smartschool.dto.mapper.SubjectMapperImpl;
import com.mper.smartschool.entity.Subject;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.repository.SubjectRepo;
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
public class SubjectServiceImplTest {

    @Mock
    private SubjectRepo subjectRepo;

    private SubjectMapper subjectMapper = new SubjectMapperImpl();

    private SubjectServiceImpl subjectService;

    private SubjectDto subjectDto;

    @BeforeEach
    public void setUp() {
        subjectService = new SubjectServiceImpl(subjectRepo, subjectMapper);
        subjectDto = DtoDirector.makeTestSubjectDtoById(1L);
    }

    @Test
    public void create_success() {
        subjectDto.setId(null);
        subjectDto.setStatus(null);
        Subject subject = subjectMapper.toEntity(subjectDto);
        subject.setStatus(EntityStatus.ACTIVE);
        Mockito.when(subjectRepo.save(subject)).thenAnswer(invocationOnMock -> {
            Subject returnedSubject = invocationOnMock.getArgument(0);
            returnedSubject.setId(1L);
            return returnedSubject;
        });

        SubjectDto result = subjectService.create(subjectDto);

        assertNotNull(result.getId());

        assertEquals(result.getStatus(), EntityStatus.ACTIVE);

        assertThat(result).isEqualToIgnoringGivenFields(subjectDto, "id", "status");
    }

    @Test
    public void update_success() {
        Subject subject = subjectMapper.toEntity(subjectDto);
        Mockito.when(subjectRepo.findById(subjectDto.getId())).thenReturn(Optional.of(subject));

        Mockito.when(subjectRepo.save(subject)).thenReturn(subject);

        SubjectDto result = subjectService.update(subjectDto);

        assertEquals(result, subjectDto);
    }

    @Test
    public void update_throwEntityNotFoundException_ifSubjectNotFound() {
        subjectDto.setId(Long.MAX_VALUE);
        Mockito.when(subjectRepo.findById(subjectDto.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> subjectService.update(subjectDto));
    }

    @Test
    public void findAll_success() {
        Collection<SubjectDto> subjectsDto = getCollectionOfSubjectsDto();
        Mockito.when(subjectRepo.findAll())
                .thenReturn(subjectsDto.stream().map(subjectMapper::toEntity).collect(Collectors.toList()));

        Collection<SubjectDto> result = subjectService.findAll();

        assertEquals(result, subjectsDto);
    }

    @Test
    public void findById_success() {
        Subject subject = subjectMapper.toEntity(subjectDto);
        Mockito.when(subjectRepo.findById(subjectDto.getId())).thenReturn(Optional.of(subject));

        SubjectDto result = subjectService.findById(subjectDto.getId());

        assertEquals(result, subjectDto);
    }

    @Test
    public void findById_throwEntityNotFoundException_ifSubjectNotFound() {
        Mockito.when(subjectRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> subjectService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        Subject subject = subjectMapper.toEntity(subjectDto);
        Mockito.when(subjectRepo.findById(subjectDto.getId())).thenReturn(Optional.of(subject));

        Mockito.when(subjectRepo.setDeletedStatusById(subject.getId())).thenReturn(1);

        assertDoesNotThrow(() -> subjectService.deleteById(subjectDto.getId()));
    }

    @Test
    public void deleteById_throwEntityNotFoundException_ifSubjectNotFound() {
        Mockito.when(subjectRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> subjectService.deleteById(Long.MAX_VALUE));
    }

    private Collection<SubjectDto> getCollectionOfSubjectsDto() {
        SubjectDto subjectDto2 = DtoDirector.makeTestSubjectDtoById(2L);
        SubjectDto subjectDto3 = DtoDirector.makeTestSubjectDtoById(3L);
        return Arrays.asList(subjectDto, subjectDto2, subjectDto3);
    }
}
