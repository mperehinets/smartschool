package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.PupilSuccessDto;
import com.mper.smartschool.dto.mapper.PupilSuccessMapper;
import com.mper.smartschool.dto.mapper.PupilSuccessMapperImpl;
import com.mper.smartschool.entity.PupilSuccess;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.PupilSuccessRepo;
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
public class PupilSuccessServiceImplTest {

    @Mock
    private PupilSuccessRepo pupilSuccessRepo;

    private final PupilSuccessMapper pupilSuccessMapper = new PupilSuccessMapperImpl();

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
        var pupilSuccess = pupilSuccessMapper.toEntity(pupilSuccessDto);
        Mockito.when(pupilSuccessRepo.save(pupilSuccess)).thenAnswer(invocationOnMock -> {
            PupilSuccess returnedPupilSuccess = invocationOnMock.getArgument(0);
            returnedPupilSuccess.setId(1L);
            return returnedPupilSuccess;
        });

        var result = pupilSuccessService.create(pupilSuccessDto);

        assertNotNull(result.getId());

        assertThat(result).isEqualToIgnoringGivenFields(pupilSuccessDto, "id");
    }

    @Test
    public void update_success() {
        var pupilSuccess = pupilSuccessMapper.toEntity(pupilSuccessDto);
        Mockito.when(pupilSuccessRepo.findById(pupilSuccessDto.getId())).thenReturn(Optional.of(pupilSuccess));

        Mockito.when(pupilSuccessRepo.save(pupilSuccess)).thenReturn(pupilSuccess);

        var result = pupilSuccessService.update(pupilSuccessDto);

        assertEquals(result, pupilSuccessDto);
    }

    @Test
    public void update_throwNotFoundException_ifPupilSuccessNotFound() {
        pupilSuccessDto.setId(Long.MAX_VALUE);
        Mockito.when(pupilSuccessRepo.findById(pupilSuccessDto.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> pupilSuccessService.update(pupilSuccessDto));
    }

    @Test
    public void findAll_success() {
        var pupilSuccessesDto = getCollectionOfPupilSuccessesDto();
        Mockito.when(pupilSuccessRepo.findAll())
                .thenReturn(pupilSuccessesDto.stream().map(pupilSuccessMapper::toEntity).collect(Collectors.toList()));

        var result = pupilSuccessService.findAll();

        assertEquals(result, pupilSuccessesDto);
    }

    @Test
    public void findById_success() {
        var pupilSuccess = pupilSuccessMapper.toEntity(pupilSuccessDto);
        Mockito.when(pupilSuccessRepo.findById(pupilSuccessDto.getId())).thenReturn(Optional.of(pupilSuccess));

        var result = pupilSuccessService.findById(pupilSuccessDto.getId());

        assertEquals(result, pupilSuccessDto);
    }

    @Test
    public void findById_throwNotFoundException_ifPupilSuccessNotFound() {
        Mockito.when(pupilSuccessRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> pupilSuccessService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        var pupilSuccess = pupilSuccessMapper.toEntity(pupilSuccessDto);
        Mockito.when(pupilSuccessRepo.findById(pupilSuccessDto.getId())).thenReturn(Optional.of(pupilSuccess));
        Mockito.doNothing().when(pupilSuccessRepo).deleteById(pupilSuccessDto.getId());
        assertDoesNotThrow(() -> pupilSuccessService.deleteById(pupilSuccessDto.getId()));
    }

    @Test
    public void deleteById_throwNotFoundException_ifPupilSuccessNotFound() {
        Mockito.when(pupilSuccessRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> pupilSuccessService.deleteById(Long.MAX_VALUE));
    }

    private Collection<PupilSuccessDto> getCollectionOfPupilSuccessesDto() {
        var pupilSuccessDto2 = DtoDirector.makeTestPupilSuccessDtoById(2L);
        var pupilSuccessDto3 = DtoDirector.makeTestPupilSuccessDtoById(3L);
        return Arrays.asList(pupilSuccessDto, pupilSuccessDto2, pupilSuccessDto3);
    }
}
