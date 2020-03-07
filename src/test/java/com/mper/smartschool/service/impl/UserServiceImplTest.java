package com.mper.smartschool.service.impl;

import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.dto.mapper.UserMapper;
import com.mper.smartschool.dto.mapper.UserMapperImpl;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.User;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.repository.RoleRepo;
import com.mper.smartschool.repository.UserRepo;
import com.mper.smartschool.service.AvatarStorageService;
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
public class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private RoleRepo roleRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AvatarStorageService avatarStorageService;

    private UserMapper userMapper = new UserMapperImpl();

    private UserServiceImpl userService;

    private UserDto userDto;


    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepo, userMapper, roleRepo, passwordEncoder, avatarStorageService);
        userDto = DtoDirector.makeTestUserDtoById(1L);
    }

    @Test
    public void create_success() {
        Role roleUser = Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .status(EntityStatus.ACTIVE)
                .build();
        Mockito.when(roleRepo.findByName(roleUser.getName())).thenReturn(Optional.of(roleUser));

        String encodedPassword = "encodedPassword";

        Mockito.when(passwordEncoder.encode(userDto.getPassword())).thenReturn(encodedPassword);

        userDto.setId(null);
        userDto.setStatus(null);
        User user = userMapper.toEntity(userDto);
        user.setRoles(Collections.singleton(roleUser));
        user.setStatus(EntityStatus.ACTIVE);
        user.setPassword(encodedPassword);
        Mockito.when(userRepo.save(user)).thenAnswer(invocationOnMock -> {
            User returnedUser = invocationOnMock.getArgument(0);
            returnedUser.setId(1L);
            return returnedUser;
        });

        UserDto result = userService.create(userDto);

        assertNotNull(result.getId());

        assertEquals(result.getStatus(), EntityStatus.ACTIVE);

        assertEquals(result.getRoles(), Stream.of(roleUser).collect(Collectors.toSet()));

        assertThat(result).isEqualToIgnoringGivenFields(userDto, "id", "roles", "status");
    }


    @Test
    public void update_success() {
        User user = userMapper.toEntity(userDto);
        Mockito.when(userRepo.findById(userDto.getId())).thenReturn(Optional.of(user));

        Mockito.when(userRepo.save(user)).thenReturn(user);

        UserDto result = userService.update(userDto);

        assertThat(result).isEqualToIgnoringGivenFields(userDto, "email", "password");
    }

    @Test
    public void update_throwNotFoundException_ifUserNotFound() {
        userDto.setId(Long.MAX_VALUE);
        Mockito.when(userRepo.findById(userDto.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.update(userDto));
    }

    @Test
    public void findAll_success() {
        Collection<UserDto> usersDto = getCollectionOfUsersDto();
        System.out.println(usersDto.toString());
        Mockito.when(userRepo.findAll())
                .thenReturn(usersDto.stream().map(userMapper::toEntity).collect(Collectors.toList()));

        Collection<UserDto> result = userService.findAll();

        assertEquals(result, usersDto);
    }

    @Test
    public void findById_success() {
        User user = userMapper.toEntity(userDto);
        Mockito.when(userRepo.findById(userDto.getId())).thenReturn(Optional.of(user));

        UserDto result = userService.findById(userDto.getId());

        assertEquals(result, userDto);
    }

    @Test
    public void findById_throwNotFoundException_ifUserNotFound() {
        Mockito.when(userRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findById(Long.MAX_VALUE));
    }

    @Test
    public void deleteById_success() {
        User user = userMapper.toEntity(userDto);
        Mockito.when(userRepo.findById(userDto.getId())).thenReturn(Optional.of(user));

        Mockito.when(userRepo.setDeletedStatusById(user.getId())).thenReturn(1);

        assertDoesNotThrow(() -> userService.deleteById(userDto.getId()));
    }

    @Test
    public void deleteById_throwNotFoundException_ifUserNotFound() {
        Mockito.when(userRepo.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.deleteById(Long.MAX_VALUE));
    }

    @Test
    public void findByEmail_success() {
        User user = userMapper.toEntity(userDto);
        Mockito.when(userRepo.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));

        UserDto result = userService.findByEmail(userDto.getEmail());

        assertEquals(result, userDto);
    }

    @Test
    public void findByEmail_throwNotFoundException_ifUserNotFound() {
        String nonExistentEmail = "hello@com";
        Mockito.when(userRepo.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findByEmail(nonExistentEmail));
    }

    private Collection<UserDto> getCollectionOfUsersDto() {
        UserDto userDto2 = DtoDirector.makeTestUserDtoById(2L);
        UserDto userDto3 = DtoDirector.makeTestUserDtoById(3L);
        return Arrays.asList(userDto, userDto2, userDto3);
    }
}
