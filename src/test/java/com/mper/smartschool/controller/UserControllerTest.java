package com.mper.smartschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.ChangeStatusDto;
import com.mper.smartschool.dto.UserDto;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = DtoDirector.makeTestUserDtoById(1L);
    }

    @Test
    public void create_return201_ifInputsIsValid() throws Exception {
        userDto.setId(null);
        userDto.setPassword("somePassword1");
        mockMvc.perform(post("/smartschool/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
        Mockito.verify(userService, Mockito.times(1)).create(userDto);
    }

    @Test
    public void create_return400_ifIdIsNotNull() throws Exception {
        mockMvc.perform(post("/smartschool/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifFirstNameIsNull() throws Exception {
        userDto.setId(null);
        userDto.setFirstName(null);
        mockMvc.perform(post("/smartschool/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifFirstNameNotMatchPattern() throws Exception {
        userDto.setId(null);
        userDto.setFirstName("firstName$~");
        mockMvc.perform(post("/smartschool/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifSecondNameIsNull() throws Exception {
        userDto.setId(null);
        userDto.setSecondName(null);
        mockMvc.perform(post("/smartschool/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifSecondNameNotMatchPattern() throws Exception {
        userDto.setId(null);
        userDto.setSecondName("firstName$~");
        mockMvc.perform(post("/smartschool/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifEmailIsNullOrEmpty() throws Exception {
        userDto.setId(null);
        userDto.setEmail("");
        mockMvc.perform(post("/smartschool/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifEmailIsInvalid() throws Exception {
        userDto.setId(null);
        userDto.setEmail("helloWorld");
        mockMvc.perform(post("/smartschool/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifPasswordIsNullOrEmpty() throws Exception {
        userDto.setId(null);
        userDto.setPassword("");
        mockMvc.perform(post("/smartschool/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifPasswordNotMatchLength() throws Exception {
        userDto.setId(null);
        userDto.setPassword("length");
        mockMvc.perform(post("/smartschool/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifDateBirthIsInFuture() throws Exception {
        userDto.setId(null);
        userDto.setDateBirth(LocalDate.now().plusMonths(1));
        mockMvc.perform(post("/smartschool/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return200_ifInputsIsValid() throws Exception {
        userDto.setRoles(Collections.singleton(new Role()));
        mockMvc.perform(put("/smartschool/users/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());
        Mockito.verify(userService, Mockito.times(1)).update(userDto);
    }

    @Test
    public void update_return404_ifIdNotExist() throws Exception {
        userDto.setRoles(Collections.singleton(new Role()));
        userDto.setId(Long.MAX_VALUE);
        Mockito.when(userService.update(userDto)).thenThrow(new NotFoundException("", null));
        mockMvc.perform(put("/smartschool/users/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_return400_ifIdIsNull() throws Exception {
        userDto.setRoles(Collections.singleton(new Role()));
        userDto.setId(null);
        mockMvc.perform(put("/smartschool/users/null")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifFirstNameIsNull() throws Exception {
        userDto.setRoles(Collections.singleton(new Role()));
        userDto.setFirstName(null);
        mockMvc.perform(put("/smartschool/users/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifFirstNameNotMatchPattern() throws Exception {
        userDto.setRoles(Collections.singleton(new Role()));
        userDto.setFirstName("firstName$~");
        mockMvc.perform(put("/smartschool/users/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifSecondNameIsNull() throws Exception {
        userDto.setRoles(Collections.singleton(new Role()));
        userDto.setSecondName(null);
        mockMvc.perform(put("/smartschool/users/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifSecondNameNotMatchPattern() throws Exception {
        userDto.setRoles(Collections.singleton(new Role()));
        userDto.setSecondName("secondName$~");
        mockMvc.perform(put("/smartschool/users/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifDateBirthIsInFuture() throws Exception {
        userDto.setRoles(Collections.singleton(new Role()));
        userDto.setDateBirth(LocalDate.now().plusMonths(1));
        mockMvc.perform(put("/smartschool/users/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAll_return200() throws Exception {
        mockMvc.perform(get("/smartschool/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(userService, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(get("/smartschool/users/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(userService, Mockito.times(1)).findById(userDto.getId());
    }

    @Test
    public void findById_return404_ifInputIdNotExist() throws Exception {
        Mockito.when(userService.findById(Long.MAX_VALUE)).thenThrow(new NotFoundException("", null));
        mockMvc.perform(get("/smartschool/users/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void changeStatusById_return200_ifInputsValid() throws Exception {
        var changeStatusDto = ChangeStatusDto.builder()
                .id(userDto.getId())
                .newStatus(EntityStatus.EXCLUDED)
                .build();

        mockMvc.perform(put("/smartschool/users/change-status/{id}", changeStatusDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeStatusDto)))
                .andExpect(status().isOk());
        Mockito.verify(userService,
                Mockito.times(1)).changeStatusById(changeStatusDto);
    }

    @Test
    public void changeStatusById_return404_ifInputIdNotExist() throws Exception {
        var changeStatusDto = ChangeStatusDto.builder()
                .id(Long.MAX_VALUE)
                .newStatus(EntityStatus.EXCLUDED)
                .build();

        Mockito.doThrow(new NotFoundException("", null)).when(userService).changeStatusById(changeStatusDto);
        mockMvc.perform(put("/smartschool/users/change-status/{id}", changeStatusDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeStatusDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByEmail_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(get("/smartschool/users/?=email=" + userDto.getEmail())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(userService, Mockito.times(1)).findByEmail(userDto.getEmail());
    }

    @Test
    public void findByEmail_return404_ifInputIdNotExist() throws Exception {
        String notExistedEmail = "hello@com";
        Mockito.when(userService.findByEmail(notExistedEmail)).thenThrow(new NotFoundException("", null));
        mockMvc.perform(get("/smartschool/users/?=email=" + notExistedEmail)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void giveAdminById_return200_ifInputsValid() throws Exception {
        mockMvc.perform(put("/smartschool/users/give-admin/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(userService, Mockito.times(1)).giveAdminById(userDto.getId());
    }

    @Test
    public void giveAdminById_return404_ifInputIdNotExist() throws Exception {
        Mockito.doThrow(new NotFoundException("", null)).when(userService).giveAdminById(Long.MAX_VALUE);
        mockMvc.perform(put("/smartschool/users/give-admin/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void takeAdminAwayById_return200_ifInputsValid() throws Exception {
        mockMvc.perform(put("/smartschool/users/take-admin-away/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(userService, Mockito.times(1)).takeAdminAwayById(userDto.getId());
    }

    @Test
    public void takeAdminAwayById_return404_ifInputIdNotExist() throws Exception {
        Mockito.doThrow(new NotFoundException("", null)).when(userService).takeAdminAwayById(Long.MAX_VALUE);
        mockMvc.perform(put("/smartschool/users/take-admin-away/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
