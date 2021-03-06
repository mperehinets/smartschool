package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.SignedPersonDto;
import com.mper.smartschool.dto.mapper.SignedPersonMapper;
import com.mper.smartschool.dto.mapper.SignedPersonMapperImpl;
import com.mper.smartschool.entity.SignedPerson;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.SignedPersonRepo;
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
public class SignedPersonServiceImplTest {

    @Mock
    private SignedPersonRepo signedPersonRepo;

    private final SignedPersonMapper signedPersonMapper = new SignedPersonMapperImpl();

    private SignedPersonServiceImpl signedPersonService;

    private SignedPersonDto signedPersonDto;

    @BeforeEach
    public void setUp() {
        signedPersonService = new SignedPersonServiceImpl(signedPersonRepo, signedPersonMapper);
        signedPersonDto = DtoDirector.makeTestSignedPersonDtoById(1L);
    }

    @Test
    public void create_success() {
        signedPersonDto.setId(null);
        var signedPerson = signedPersonMapper.toEntity(signedPersonDto);
        Mockito.when(signedPersonRepo.save(signedPerson)).thenAnswer(invocationOnMock -> {
            SignedPerson returnedSignedPerson = invocationOnMock.getArgument(0);
            returnedSignedPerson.setId(1L);
            return returnedSignedPerson;
        });

        SignedPersonDto result = signedPersonService.create(signedPersonDto);

        assertNotNull(result.getId());

        assertThat(result).isEqualToIgnoringGivenFields(signedPersonDto, "id");
    }

    @Test
    public void update_success() {
        var signedPerson = signedPersonMapper.toEntity(signedPersonDto);
        Mockito.when(signedPersonRepo.findById(signedPersonDto.getId())).thenReturn(Optional.of(signedPerson));

        Mockito.when(signedPersonRepo.save(signedPerson)).thenReturn(signedPerson);

        SignedPersonDto result = signedPersonService.update(signedPersonDto);

        assertEquals(result, signedPersonDto);
    }

    @Test
    public void update_throwNotFoundException_ifSignedPersonNotFound() {
        signedPersonDto.setId(Long.MAX_VALUE);
        Mockito.when(signedPersonRepo.findById(signedPersonDto.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> signedPersonService.update(signedPersonDto));
    }

    @Test
    public void findAll_success() {
        var signedPersonsDto = getCollectionOfSignedPersonsDto();
        Mockito.when(signedPersonRepo.findAll())
                .thenReturn(signedPersonsDto.stream().map(signedPersonMapper::toEntity).collect(Collectors.toList()));

        var result = signedPersonService.findAll();

        assertEquals(result, signedPersonsDto);
    }

    @Test
    public void findById_success() {
        var signedPerson = signedPersonMapper.toEntity(signedPersonDto);
        Mockito.when(signedPersonRepo.findById(signedPersonDto.getId())).thenReturn(Optional.of(signedPerson));

        var result = signedPersonService.findById(signedPersonDto.getId());

        assertEquals(result, signedPersonDto);
    }

    @Test
    public void findById_throwNotFoundException_ifSignedPersonNotFound() {
        Mockito.when(signedPersonRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> signedPersonService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        var signedPerson = signedPersonMapper.toEntity(signedPersonDto);
        Mockito.when(signedPersonRepo.findById(signedPersonDto.getId())).thenReturn(Optional.of(signedPerson));
        Mockito.doNothing().when(signedPersonRepo).deleteById(signedPersonDto.getId());
        assertDoesNotThrow(() -> signedPersonService.deleteById(signedPersonDto.getId()));
    }

    @Test
    public void deleteById_throwNotFoundException_ifSignedPersonNotFound() {
        Mockito.when(signedPersonRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> signedPersonService.deleteById(Long.MAX_VALUE));
    }

    private Collection<SignedPersonDto> getCollectionOfSignedPersonsDto() {
        var signedPersonDto2 = DtoDirector.makeTestSignedPersonDtoById(2L);
        var signedPersonDto3 = DtoDirector.makeTestSignedPersonDtoById(3L);
        return Arrays.asList(signedPersonDto, signedPersonDto2, signedPersonDto3);
    }
}
