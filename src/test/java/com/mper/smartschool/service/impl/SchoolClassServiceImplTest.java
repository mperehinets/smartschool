package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.dto.mapper.SchoolClassMapper;
import com.mper.smartschool.dto.mapper.SchoolClassMapperImpl;
import com.mper.smartschool.entity.Schedule;
import com.mper.smartschool.entity.SchoolClass;
import com.mper.smartschool.entity.modelsEnum.SchoolClassInitial;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.exception.SchoolFilledByClassesException;
import com.mper.smartschool.repository.PupilRepo;
import com.mper.smartschool.repository.ScheduleRepo;
import com.mper.smartschool.repository.SchoolClassRepo;
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
public class SchoolClassServiceImplTest {

    @Mock
    private SchoolClassRepo schoolClassRepo;

    @Mock
    private PupilRepo pupilRepo;

    @Mock
    private ScheduleRepo scheduleRepo;

    private final SchoolClassMapper schoolClassMapper = new SchoolClassMapperImpl();

    private SchoolClassServiceImpl schoolClassService;

    private SchoolClassDto schoolClassDto;

    @BeforeEach
    public void setUp() {
        schoolClassService = new SchoolClassServiceImpl(schoolClassRepo, schoolClassMapper, pupilRepo, scheduleRepo);
        schoolClassDto = DtoDirector.makeTestSchoolClassDtoById(1L);
    }

    @Test
    public void create_success() {
        schoolClassDto.setId(null);
        schoolClassDto.setInitial(null);

        var lastSchoolClass = schoolClassMapper.toEntity(schoolClassDto);
        lastSchoolClass.setInitial(SchoolClassInitial.A);
        lastSchoolClass.setId(5L);
        Mockito.when(schoolClassRepo
                .findLastByNumber(schoolClassDto.getNumber()))
                .thenReturn(lastSchoolClass);

        var schoolClass = schoolClassMapper.toEntity(schoolClassDto);
        schoolClass.setInitial(SchoolClassInitial.B);
        Mockito.when(schoolClassRepo.save(schoolClass)).thenAnswer(invocationOnMock -> {
            SchoolClass returnedSchoolClass = invocationOnMock.getArgument(0);
            returnedSchoolClass.setId(1L);
            return returnedSchoolClass;
        });

        var result = schoolClassService.create(schoolClassDto);

        assertNotNull(result.getId());

        assertEquals(result.getInitial(), SchoolClassInitial.B);

        assertThat(result).isEqualToIgnoringGivenFields(schoolClassDto,
                "id");
    }

    @Test
    public void create_throwSchoolFilledByClassesException_ifSchoolFilledByClasses() {
        schoolClassDto.setId(null);

        var lastSchoolClass = schoolClassMapper.toEntity(schoolClassDto);
        lastSchoolClass.setInitial(SchoolClassInitial.F);
        lastSchoolClass.setId(5L);
        Mockito.when(schoolClassRepo
                .findLastByNumber(schoolClassDto.getNumber()))
                .thenReturn(lastSchoolClass);

        assertThrows(SchoolFilledByClassesException.class, () -> schoolClassService.create(schoolClassDto));
    }

    @Test
    public void update_success() {
        var schoolClass = schoolClassMapper.toEntity(schoolClassDto);
        Mockito.when(schoolClassRepo.findById(schoolClassDto.getId())).thenReturn(Optional.of(schoolClass));

        Mockito.when(schoolClassRepo.save(schoolClass)).thenReturn(schoolClass);

        var result = schoolClassService.update(schoolClassDto);

        assertEquals(result, schoolClassDto);
    }

    @Test
    public void update_throwNotFoundException_ifSchoolClassNotFound() {
        schoolClassDto.setId(Long.MAX_VALUE);
        Mockito.when(schoolClassRepo.findById(schoolClassDto.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> schoolClassService.update(schoolClassDto));
    }

    @Test
    public void findAll_success() {
        var schoolClassesDto = getCollectionOfSchoolClassesDto();
        Mockito.when(schoolClassRepo.findAll())
                .thenReturn(schoolClassesDto.stream().map(schoolClassMapper::toEntity).collect(Collectors.toList()));

        for (var schoolClassDto : schoolClassesDto) {
            Mockito.when(scheduleRepo.findLastByClassId(schoolClassDto.getId()))
                    .thenReturn(new Schedule());
        }

        var result = schoolClassService.findAll();

        assertEquals(result, schoolClassesDto
                .stream()
                .peek(item -> item.setPupilsCount(0))
                .peek(item -> item.setLastScheduleDate(null))
                .collect(Collectors.toList()));
    }

    @Test
    public void findById_success() {
        var schoolClass = schoolClassMapper.toEntity(schoolClassDto);
        Mockito.when(schoolClassRepo.findById(schoolClassDto.getId())).thenReturn(Optional.of(schoolClass));

        var result = schoolClassService.findById(schoolClassDto.getId());

        assertEquals(result, schoolClassDto);
    }

    @Test
    public void findById_throwNotFoundException_ifSchoolClassNotFound() {
        Mockito.when(schoolClassRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> schoolClassService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        var schoolClass = schoolClassMapper.toEntity(schoolClassDto);
        Mockito.when(schoolClassRepo.findById(schoolClassDto.getId())).thenReturn(Optional.of(schoolClass));

        Mockito.doNothing().when(schoolClassRepo).deleteById(schoolClass.getId());

        assertDoesNotThrow(() -> schoolClassService.deleteById(schoolClassDto.getId()));
    }

    @Test
    public void deleteById_throwNotFoundException_ifSchoolClassNotFound() {
        Mockito.when(schoolClassRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> schoolClassService.deleteById(Long.MAX_VALUE));
    }

    private Collection<SchoolClassDto> getCollectionOfSchoolClassesDto() {
        var schoolClassDto2 = DtoDirector.makeTestSchoolClassDtoById(2L);
        var schoolClassDto3 = DtoDirector.makeTestSchoolClassDtoById(3L);
        return Arrays.asList(schoolClassDto, schoolClassDto2, schoolClassDto3);
    }
}
