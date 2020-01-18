package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.PupilDto;
import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.dto.mapper.PupilMapper;
import com.mper.smartschool.dto.mapper.PupilMapperImpl;
import com.mper.smartschool.entity.Pupil;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.repository.PupilRepo;
import com.mper.smartschool.repository.RoleRepo;
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
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PupilServiceImplTest {

    @Mock
    private PupilRepo pupilRepo;

    @Mock
    private RoleRepo roleRepo;

    private PupilMapper pupilMapper = new PupilMapperImpl();

    private PupilServiceImpl pupilService;

    private PupilDto pupilDto;

    @BeforeEach
    public void setUp() {
        pupilService = new PupilServiceImpl(pupilRepo, pupilMapper, roleRepo);
        pupilDto = DtoDirector.makeTestPupilById(1L);
    }

    @Test
    public void create_success() {
        Role rolePupil = Role.builder()
                .id(1L)
                .name("ROLE_PUPIL")
                .status(EntityStatus.ACTIVE)
                .build();
        Mockito.when(roleRepo.findByName(rolePupil.getName())).thenReturn(Optional.of(rolePupil));

        pupilDto.setId(null);
        pupilDto.setStatus(null);
        Pupil pupil = pupilMapper.toEntity(pupilDto);
        pupil.getRoles().add(rolePupil);
        pupil.setStatus(EntityStatus.ACTIVE);
        Mockito.when(pupilRepo.save(pupil)).thenAnswer(invocationOnMock -> {
            Pupil returnedPupil = invocationOnMock.getArgument(0);
            returnedPupil.setId(1L);
            return returnedPupil;
        });

        PupilDto result = pupilService.create(pupilDto);

        assertNotNull(result.getId());

        assertEquals(result.getStatus(), EntityStatus.ACTIVE);

        assertEquals(result.getRoles(), Stream.of(rolePupil).collect(Collectors.toSet()));

        assertThat(result).isEqualToIgnoringGivenFields(pupilDto, "id", "roles", "status");
    }

    @Test
    public void update_success() {
        Pupil pupil = pupilMapper.toEntity(pupilDto);
        Mockito.when(pupilRepo.findById(pupilDto.getId())).thenReturn(Optional.of(pupil));

        Mockito.when(pupilRepo.save(pupil)).thenReturn(pupil);

        PupilDto result = pupilService.update(pupilDto);

        assertEquals(result, pupilDto);
    }

    @Test
    public void update_throwEntityNotFoundException_ifPupilNotFound() {
        pupilDto.setId(Long.MAX_VALUE);
        Mockito.when(pupilRepo.findById(pupilDto.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> pupilService.update(pupilDto));
    }

    @Test
    public void findAll_success() {
        Collection<PupilDto> pupilsDto = getCollectionOfPupilsDto();
        Mockito.when(pupilRepo.findAll())
                .thenReturn(pupilsDto.stream().map(pupilMapper::toEntity).collect(Collectors.toList()));

        Collection<PupilDto> result = pupilService.findAll();

        assertEquals(result, pupilsDto);
    }

    @Test
    public void findById_success() {
        Pupil pupil = pupilMapper.toEntity(pupilDto);
        Mockito.when(pupilRepo.findById(pupilDto.getId())).thenReturn(Optional.of(pupil));

        UserDto result = pupilService.findById(pupilDto.getId());

        assertEquals(result, pupilDto);
    }

    @Test
    public void findById_throwEntityNotFoundException_ifPupilNotFound() {
        Mockito.when(pupilRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> pupilService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        Pupil pupil = pupilMapper.toEntity(pupilDto);
        Mockito.when(pupilRepo.findById(pupilDto.getId())).thenReturn(Optional.of(pupil));

        pupil.setStatus(EntityStatus.DELETED);
        Mockito.when(pupilRepo.save(pupil)).thenReturn(pupil);

        assertDoesNotThrow(() -> pupilService.deleteById(pupilDto.getId()));
    }

    @Test
    public void deleteById_throwEntityNotFoundException_ifPupilNotFound() {
        Mockito.when(pupilRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> pupilService.deleteById(Long.MAX_VALUE));
    }

    private Collection<PupilDto> getCollectionOfPupilsDto() {
        PupilDto pupilDto2 = DtoDirector.makeTestPupilById(2L);
        PupilDto pupilDto3 = DtoDirector.makeTestPupilById(3L);
        return Arrays.asList(pupilDto, pupilDto2, pupilDto3);
    }
}
