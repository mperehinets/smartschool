package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.TeacherDto;
import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.dto.mapper.TeacherMapper;
import com.mper.smartschool.dto.mapper.TeacherMapperImpl;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.Teacher;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.RoleRepo;
import com.mper.smartschool.repository.TeacherRepo;
import com.mper.smartschool.repository.TeachersSubjectRepo;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceImplTest {

    @Mock
    private TeacherRepo teacherRepo;

    @Mock
    private RoleRepo roleRepo;

    @Mock
    private TeachersSubjectRepo teachersSubjectRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AvatarStorageService avatarStorageService;

    private final TeacherMapper teacherMapper = new TeacherMapperImpl();
    @Mock
    private EmailService emailService;

    private TeacherServiceImpl teacherService;

    private TeacherDto teacherDto;

    @BeforeEach
    public void setUp() {
        teacherService = new TeacherServiceImpl(teacherRepo,
                teacherMapper,
                roleRepo,
                teachersSubjectRepo,
                passwordEncoder,
                avatarStorageService,
                emailService);
        teacherDto = DtoDirector.makeTestTeacherDtoById(1L);
    }

    @Test
    public void create_success() {
        Role roleTeacher = Role.builder()
                .id(1L)
                .name("ROLE_TEACHER")
                .status(EntityStatus.ACTIVE)
                .build();
        Mockito.when(roleRepo.findTeacherRole()).thenReturn(roleTeacher);

        String encodedPassword = "encodedPassword";

        Mockito.when(passwordEncoder.encode(teacherDto.getPassword())).thenReturn(encodedPassword);

        teacherDto.setId(null);
        teacherDto.setStatus(null);
        Teacher teacher = teacherMapper.toEntity(teacherDto);
        teacher.setRoles(Collections.singleton(roleTeacher));
        teacher.setStatus(EntityStatus.ACTIVE);
        teacher.setPassword(encodedPassword);
        Mockito.when(teacherRepo.save(teacher)).thenAnswer(invocationOnMock -> {
            Teacher returnedTeacher = invocationOnMock.getArgument(0);
            returnedTeacher.setId(1L);
            return returnedTeacher;
        });

        TeacherDto result = teacherService.create(teacherDto);

        assertNotNull(result.getId());

        assertEquals(result.getStatus(), EntityStatus.ACTIVE);

        assertThat(result).isEqualToIgnoringGivenFields(teacherDto,
                "id",
                "password",
                "roles",
                "status");
    }

    @Test
    public void update_success() {
        Teacher teacher = teacherMapper.toEntity(teacherDto);
        Mockito.when(teacherRepo.findById(teacherDto.getId())).thenReturn(Optional.of(teacher));

        Mockito.when(teacherRepo.save(teacher)).thenReturn(teacher);

        TeacherDto result = teacherService.update(teacherDto);

        assertEquals(result, teacherDto);
    }

    @Test
    public void update_throwNotFoundException_ifTeacherNotFound() {
        teacherDto.setId(Long.MAX_VALUE);
        Mockito.when(teacherRepo.findById(teacherDto.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> teacherService.update(teacherDto));
    }

    @Test
    public void findAll_success() {
        Collection<TeacherDto> teachersDto = getCollectionOfTeachersDto();
        Mockito.when(teacherRepo.findAll())
                .thenReturn(teachersDto.stream().map(teacherMapper::toEntity).collect(Collectors.toList()));

        Collection<TeacherDto> result = teacherService.findAll();

        assertEquals(result, teachersDto
                .stream()
                .peek(item -> item.setSubjectsCount(0))
                .collect(Collectors.toList()));
    }

    @Test
    public void findById_success() {
        Teacher teacher = teacherMapper.toEntity(teacherDto);
        Mockito.when(teacherRepo.findById(teacherDto.getId())).thenReturn(Optional.of(teacher));

        UserDto result = teacherService.findById(teacherDto.getId());

        assertEquals(result, teacherDto);
    }

    @Test
    public void findById_throwNotFoundException_ifTeacherNotFound() {
        Mockito.when(teacherRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> teacherService.findById(Long.MAX_VALUE));
    }

    private Collection<TeacherDto> getCollectionOfTeachersDto() {
        TeacherDto teacherDto2 = DtoDirector.makeTestTeacherDtoById(2L);
        TeacherDto teacherDto3 = DtoDirector.makeTestTeacherDtoById(3L);
        return Arrays.asList(teacherDto, teacherDto2, teacherDto3);
    }
}
