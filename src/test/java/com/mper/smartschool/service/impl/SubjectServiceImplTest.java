package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.ChangeStatusDto;
import com.mper.smartschool.dto.SubjectDto;
import com.mper.smartschool.dto.mapper.SubjectMapper;
import com.mper.smartschool.dto.mapper.SubjectMapperImpl;
import com.mper.smartschool.entity.Subject;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.SubjectRepo;
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
public class SubjectServiceImplTest {

    @Mock
    private SubjectRepo subjectRepo;

    private final SubjectMapper subjectMapper = new SubjectMapperImpl();

    private SubjectServiceImpl subjectService;

    private SubjectDto subjectDto;

    @BeforeEach
    public void setUp() {
        subjectService = new SubjectServiceImpl(subjectRepo, subjectMapper);
        subjectDto = DtoDirector.makeTestSubjectDtoById(1L);
        subjectDto.setStartClassInterval(1);
        subjectDto.setEndClassInterval(11);
    }

    @Test
    public void create_success() {
        subjectDto.setId(null);
        subjectDto.setStatus(null);
        var subject = subjectMapper.toEntity(subjectDto);
        subject.setStatus(EntityStatus.ACTIVE);
        Mockito.when(subjectRepo.save(subject)).thenAnswer(invocationOnMock -> {
            Subject returnedSubject = invocationOnMock.getArgument(0);
            returnedSubject.setId(1L);
            return returnedSubject;
        });

        var result = subjectService.create(subjectDto);

        assertNotNull(result.getId());

        assertEquals(result.getStatus(), EntityStatus.ACTIVE);

        assertThat(result).isEqualToIgnoringGivenFields(subjectDto, "id", "status");
    }

    @Test
    public void update_success() {
        var subject = subjectMapper.toEntity(subjectDto);
        subject.setSchoolClassInterval(subjectDto.getStartClassInterval() + "-" + subjectDto.getEndClassInterval());
        Mockito.when(subjectRepo.findById(subjectDto.getId())).thenReturn(Optional.of(subject));

        Mockito.when(subjectRepo.save(subject)).thenReturn(subject);

        var result = subjectService.update(subjectDto);

        assertEquals(result, subjectDto);
    }

    @Test
    public void update_throwNotFoundException_ifSubjectNotFound() {
        subjectDto.setId(Long.MAX_VALUE);
        Mockito.when(subjectRepo.findById(subjectDto.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> subjectService.update(subjectDto));
    }

    @Test
    public void findAll_success() {
        var subjectsDto = getCollectionOfSubjectsDto();
        Mockito.when(subjectRepo.findAll())
                .thenReturn(subjectsDto.stream()
                        .map((subjectDto) -> {
                            var subject = subjectMapper.toEntity(subjectDto);
                            subject.setSchoolClassInterval(String.format("%d-%d",
                                    subjectDto.getStartClassInterval(),
                                    subjectDto.getEndClassInterval()));
                            return subject;
                        })
                        .collect(Collectors.toList()));

        var result = subjectService.findAll();

        assertEquals(result, subjectsDto);
    }

    @Test
    public void findById_success() {
        var subject = subjectMapper.toEntity(subjectDto);
        subject.setSchoolClassInterval(subjectDto.getStartClassInterval() + "-" + subjectDto.getEndClassInterval());
        Mockito.when(subjectRepo.findById(subjectDto.getId())).thenReturn(Optional.of(subject));

        var result = subjectService.findById(subjectDto.getId());

        assertEquals(result, subjectDto);
    }

    @Test
    public void findById_throwNotFoundException_ifSubjectNotFound() {
        Mockito.when(subjectRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> subjectService.findById(Long.MAX_VALUE));
    }

    @Test
    public void changeStatusById_success() {
        var subject = subjectMapper.toEntity(subjectDto);
        subject.setSchoolClassInterval(subjectDto.getStartClassInterval() + "-" + subjectDto.getEndClassInterval());
        Mockito.when(subjectRepo.findById(subjectDto.getId())).thenReturn(Optional.of(subject));

        var changeStatusDto = ChangeStatusDto.builder()
                .id(subject.getId())
                .newStatus(EntityStatus.EXCLUDED)
                .build();

        Mockito.when(subjectRepo.changeStatusById(changeStatusDto.getId(),
                changeStatusDto.getNewStatus())).thenReturn(1);

        assertDoesNotThrow(() -> subjectService.changeStatusById(changeStatusDto));
    }

    @Test
    public void changeStatusById_throwNotFoundException_ifSubjectNotFound() {
        var changeStatusDto = ChangeStatusDto.builder()
                .id(Long.MAX_VALUE)
                .newStatus(EntityStatus.EXCLUDED)
                .build();
        Mockito.when(subjectRepo.findById(changeStatusDto.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> subjectService.changeStatusById(changeStatusDto));
    }

    private Collection<SubjectDto> getCollectionOfSubjectsDto() {
        var subjectDto2 = DtoDirector.makeTestSubjectDtoById(2L);
        subjectDto2.setStartClassInterval(1);
        subjectDto2.setEndClassInterval(11);
        var subjectDto3 = DtoDirector.makeTestSubjectDtoById(3L);
        subjectDto3.setStartClassInterval(1);
        subjectDto3.setEndClassInterval(11);
        return Arrays.asList(subjectDto, subjectDto2, subjectDto3);
    }
}
