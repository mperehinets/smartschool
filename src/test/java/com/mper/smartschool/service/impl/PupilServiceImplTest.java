package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.PupilDto;
import com.mper.smartschool.dto.mapper.PupilMapper;
import com.mper.smartschool.dto.mapper.PupilMapperImpl;
import com.mper.smartschool.entity.Pupil;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.PupilRepo;
import com.mper.smartschool.repository.RoleRepo;
import com.mper.smartschool.service.AvatarStorageService;
import com.mper.smartschool.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AvatarStorageService avatarStorageService;

    @Mock
    private EmailService emailService;

    private final PupilMapper pupilMapper = new PupilMapperImpl();

    private PupilServiceImpl pupilService;

    private PupilDto pupilDto;

    @BeforeEach
    public void setUp() {
        pupilService = new PupilServiceImpl(pupilRepo,
                pupilMapper,
                roleRepo,
                passwordEncoder,
                avatarStorageService,
                emailService);
        pupilDto = DtoDirector.makeTestPupilDtoById(1L);
    }

    @Test
    public void create_success() {
        var rolePupil = Role.builder()
                .id(1L)
                .name("ROLE_PUPIL")
                .status(EntityStatus.ACTIVE)
                .build();
        Mockito.when(roleRepo.findPupilRole()).thenReturn(rolePupil);

        String encodedPassword = "encodedPassword";

        Mockito.when(passwordEncoder.encode(pupilDto.getPassword())).thenReturn(encodedPassword);

        pupilDto.setId(null);
        pupilDto.setStatus(null);
        var pupil = pupilMapper.toEntity(pupilDto);
        pupil.setRoles(Collections.singleton(rolePupil));
        pupil.setStatus(EntityStatus.ACTIVE);
        pupil.setPassword(encodedPassword);
        Mockito.when(pupilRepo.save(pupil)).thenAnswer(invocationOnMock -> {
            Pupil returnedPupil = invocationOnMock.getArgument(0);
            returnedPupil.setId(1L);
            return returnedPupil;
        });

        var result = pupilService.create(pupilDto);

        assertNotNull(result.getId());

        assertEquals(result.getStatus(), EntityStatus.ACTIVE);

        assertEquals(result.getRoles(), Stream.of(rolePupil).collect(Collectors.toSet()));

        assertThat(result).isEqualToIgnoringGivenFields(pupilDto,
                "id",
                "roles",
                "status",
                "password");
    }

    @Test
    public void update_success() {
        var pupil = pupilMapper.toEntity(pupilDto);
        Mockito.when(pupilRepo.findById(pupilDto.getId())).thenReturn(Optional.of(pupil));

        Mockito.when(pupilRepo.save(pupil)).thenReturn(pupil);

        var result = pupilService.update(pupilDto);

        assertThat(result).isEqualToIgnoringGivenFields(pupilDto, "email", "password");
    }

    @Test
    public void update_throwNotFoundException_ifPupilNotFound() {
        pupilDto.setId(Long.MAX_VALUE);
        Mockito.when(pupilRepo.findById(pupilDto.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> pupilService.update(pupilDto));
    }

    @Test
    public void findAll_success() {
        var pupilsDto = getCollectionOfPupilsDto();
        Mockito.when(pupilRepo.findAll())
                .thenReturn(pupilsDto.stream().map(pupilMapper::toEntity).collect(Collectors.toList()));

        var result = pupilService.findAll();

        assertEquals(result, pupilsDto);
    }

    @Test
    public void findById_success() {
        var pupil = pupilMapper.toEntity(pupilDto);
        Mockito.when(pupilRepo.findById(pupilDto.getId())).thenReturn(Optional.of(pupil));

        var result = pupilService.findById(pupilDto.getId());

        assertEquals(result, pupilDto);
    }

    @Test
    public void findById_throwNotFoundException_ifPupilNotFound() {
        Mockito.when(pupilRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> pupilService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        var pupil = pupilMapper.toEntity(pupilDto);
        Mockito.when(pupilRepo.findById(pupilDto.getId())).thenReturn(Optional.of(pupil));

        pupil.setStatus(EntityStatus.DELETED);
        Mockito.when(pupilRepo.save(pupil)).thenReturn(pupil);

        assertDoesNotThrow(() -> pupilService.deleteById(pupilDto.getId()));
    }

    @Test
    public void deleteById_throwNotFoundException_ifPupilNotFound() {
        Mockito.when(pupilRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> pupilService.deleteById(Long.MAX_VALUE));
    }

    private Collection<PupilDto> getCollectionOfPupilsDto() {
        var pupilDto2 = DtoDirector.makeTestPupilDtoById(2L);
        var pupilDto3 = DtoDirector.makeTestPupilDtoById(3L);
        return Arrays.asList(pupilDto, pupilDto2, pupilDto3);
    }
}
