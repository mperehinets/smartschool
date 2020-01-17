package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.dto.mapper.SchoolClassMapper;
import com.mper.smartschool.dto.mapper.SchoolClassMapperImpl;
import com.mper.smartschool.model.SchoolClass;
import com.mper.smartschool.model.modelsEnum.EntityStatus;
import com.mper.smartschool.repository.SchoolClassRepo;
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
public class SchoolClassServiceImplTest {

    @Mock
    private SchoolClassRepo schoolClassRepo;

    private SchoolClassMapper schoolClassMapper = new SchoolClassMapperImpl();

    private SchoolClassServiceImpl schoolClassService;

    private SchoolClassDto schoolClassDto;

    @BeforeEach
    public void setUp() {
        schoolClassService = new SchoolClassServiceImpl(schoolClassRepo, schoolClassMapper);
        schoolClassDto = DtoDirector.makeTestSchoolClassDtoById(1L);
    }

    @Test
    public void create_success() {
        schoolClassDto.setId(null);
        schoolClassDto.setStatus(null);
        SchoolClass schoolClass = schoolClassMapper.toEntity(schoolClassDto);
        schoolClass.setStatus(EntityStatus.ACTIVE);
        Mockito.when(schoolClassRepo.save(schoolClass)).thenAnswer(invocationOnMock -> {
            SchoolClass returnedSchoolClass = invocationOnMock.getArgument(0);
            returnedSchoolClass.setId(1L);
            return returnedSchoolClass;
        });

        SchoolClassDto result = schoolClassService.create(schoolClassDto);

        assertNotNull(result.getId());

        assertEquals(result.getStatus(), EntityStatus.ACTIVE);

        assertThat(result).isEqualToIgnoringGivenFields(schoolClassDto, "id", "status");
    }

    @Test
    public void update_success() {
        SchoolClass schoolClass = schoolClassMapper.toEntity(schoolClassDto);
        Mockito.when(schoolClassRepo.findById(schoolClassDto.getId())).thenReturn(Optional.of(schoolClass));

        Mockito.when(schoolClassRepo.save(schoolClass)).thenReturn(schoolClass);

        SchoolClassDto result = schoolClassService.update(schoolClassDto);

        assertEquals(result, schoolClassDto);
    }

    @Test
    public void update_throwEntityNotFoundException_ifSchoolClassNotFound() {
        schoolClassDto.setId(Long.MAX_VALUE);
        Mockito.when(schoolClassRepo.findById(schoolClassDto.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> schoolClassService.update(schoolClassDto));
    }

    @Test
    public void findAll_success() {
        Collection<SchoolClassDto> schoolClassesDto = getCollectionOfSchoolClassesDto();
        Mockito.when(schoolClassRepo.findAll())
                .thenReturn(schoolClassesDto.stream().map(schoolClassMapper::toEntity).collect(Collectors.toList()));

        Collection<SchoolClassDto> result = schoolClassService.findAll();

        assertEquals(result, schoolClassesDto);
    }

    @Test
    public void findById_success() {
        SchoolClass schoolClass = schoolClassMapper.toEntity(schoolClassDto);
        Mockito.when(schoolClassRepo.findById(schoolClassDto.getId())).thenReturn(Optional.of(schoolClass));

        SchoolClassDto result = schoolClassService.findById(schoolClassDto.getId());

        assertEquals(result, schoolClassDto);
    }

    @Test
    public void findById_throwEntityNotFoundException_ifSchoolClassNotFound() {
        Mockito.when(schoolClassRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> schoolClassService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        SchoolClass schoolClass = schoolClassMapper.toEntity(schoolClassDto);
        Mockito.when(schoolClassRepo.findById(schoolClassDto.getId())).thenReturn(Optional.of(schoolClass));

        schoolClass.setStatus(EntityStatus.DELETED);
        Mockito.when(schoolClassRepo.save(schoolClass)).thenReturn(schoolClass);

        assertDoesNotThrow(() -> schoolClassService.deleteById(schoolClassDto.getId()));
    }

    @Test
    public void deleteById_throwEntityNotFoundException_ifSchoolClassNotFound() {
        Mockito.when(schoolClassRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> schoolClassService.deleteById(Long.MAX_VALUE));
    }

    private Collection<SchoolClassDto> getCollectionOfSchoolClassesDto() {
        SchoolClassDto schoolClassDto2 = DtoDirector.makeTestSchoolClassDtoById(2L);
        SchoolClassDto schoolClassDto3 = DtoDirector.makeTestSchoolClassDtoById(3L);
        return Arrays.asList(schoolClassDto, schoolClassDto2, schoolClassDto3);
    }
}
