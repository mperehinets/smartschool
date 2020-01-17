package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.PupilSuccessDto;
import com.mper.smartschool.dto.mapper.PupilSuccessMapper;
import com.mper.smartschool.dto.mapper.PupilSuccessMapperImpl;
import com.mper.smartschool.model.PupilSuccess;
import com.mper.smartschool.repository.PupilSuccessRepo;
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
public class PupilSuccessServiceImplTest {

    @Mock
    private PupilSuccessRepo pupilSuccessRepo;

    private PupilSuccessMapper pupilSuccessMapper = new PupilSuccessMapperImpl();

    private PupilSuccessServiceImpl pupilSuccessService;

    private PupilSuccessDto pupilSuccessDto;

    @BeforeEach
    public void setUp() {
        pupilSuccessService = new PupilSuccessServiceImpl(pupilSuccessRepo, pupilSuccessMapper);
        pupilSuccessDto = DtoDirector.makeTestPupilSuccessDtoById(1L);
    }

    @Test
    public void create_success() {
        pupilSuccessDto.setId(null);
        PupilSuccess pupilSuccess = pupilSuccessMapper.toEntity(pupilSuccessDto);
        Mockito.when(pupilSuccessRepo.save(pupilSuccess)).thenAnswer(invocationOnMock -> {
            PupilSuccess returnedPupilSuccess = invocationOnMock.getArgument(0);
            returnedPupilSuccess.setId(1L);
            return returnedPupilSuccess;
        });

        PupilSuccessDto result = pupilSuccessService.create(pupilSuccessDto);

        assertNotNull(result.getId());

        assertThat(result).isEqualToIgnoringGivenFields(pupilSuccessDto, "id");
    }

    @Test
    public void update_success() {
        PupilSuccess pupilSuccess = pupilSuccessMapper.toEntity(pupilSuccessDto);
        Mockito.when(pupilSuccessRepo.findById(pupilSuccessDto.getId())).thenReturn(Optional.of(pupilSuccess));

        Mockito.when(pupilSuccessRepo.save(pupilSuccess)).thenReturn(pupilSuccess);

        PupilSuccessDto result = pupilSuccessService.update(pupilSuccessDto);

        assertEquals(result, pupilSuccessDto);
    }

    @Test
    public void update_throwEntityNotFoundException_ifPupilSuccessNotFound() {
        pupilSuccessDto.setId(Long.MAX_VALUE);
        Mockito.when(pupilSuccessRepo.findById(pupilSuccessDto.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> pupilSuccessService.update(pupilSuccessDto));
    }

    @Test
    public void findAll_success() {
        Collection<PupilSuccessDto> pupilSuccessesDto = getCollectionOfPupilSuccessesDto();
        Mockito.when(pupilSuccessRepo.findAll())
                .thenReturn(pupilSuccessesDto.stream().map(pupilSuccessMapper::toEntity).collect(Collectors.toList()));

        Collection<PupilSuccessDto> result = pupilSuccessService.findAll();

        assertEquals(result, pupilSuccessesDto);
    }

    @Test
    public void findById_success() {
        PupilSuccess pupilSuccess = pupilSuccessMapper.toEntity(pupilSuccessDto);
        Mockito.when(pupilSuccessRepo.findById(pupilSuccessDto.getId())).thenReturn(Optional.of(pupilSuccess));

        PupilSuccessDto result = pupilSuccessService.findById(pupilSuccessDto.getId());

        assertEquals(result, pupilSuccessDto);
    }

    @Test
    public void findById_throwEntityNotFoundException_ifPupilSuccessNotFound() {
        Mockito.when(pupilSuccessRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> pupilSuccessService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        PupilSuccess pupilSuccess = pupilSuccessMapper.toEntity(pupilSuccessDto);
        Mockito.when(pupilSuccessRepo.findById(pupilSuccessDto.getId())).thenReturn(Optional.of(pupilSuccess));
        Mockito.doNothing().when(pupilSuccessRepo).deleteById(pupilSuccessDto.getId());
        assertDoesNotThrow(() -> pupilSuccessService.deleteById(pupilSuccessDto.getId()));
    }

    @Test
    public void deleteById_throwEntityNotFoundException_ifPupilSuccessNotFound() {
        Mockito.when(pupilSuccessRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> pupilSuccessService.deleteById(Long.MAX_VALUE));
    }

    private Collection<PupilSuccessDto> getCollectionOfPupilSuccessesDto() {
        PupilSuccessDto pupilSuccessDto2 = DtoDirector.makeTestPupilSuccessDtoById(2L);
        PupilSuccessDto pupilSuccessDto3 = DtoDirector.makeTestPupilSuccessDtoById(3L);
        return Arrays.asList(pupilSuccessDto, pupilSuccessDto2, pupilSuccessDto3);
    }
}
