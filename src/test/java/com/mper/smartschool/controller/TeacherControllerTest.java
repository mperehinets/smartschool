package com.mper.smartschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.TeacherDto;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @MockBean
    private TeacherService teacherService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TeacherDto teacherDto;

    @BeforeEach
    void setUp() {
        teacherDto = DtoDirector.makeTestTeacherDtoById(1L);
    }

    @Test
    public void create_return201_ifInputsIsValid() throws Exception {
        teacherDto.setId(null);
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isCreated());
        Mockito.verify(teacherService, Mockito.times(1)).create(teacherDto);
    }

    @Test
    public void create_return400_ifIdIsNotNull() throws Exception {
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifFirstNameIsNull() throws Exception {
        teacherDto.setId(null);
        teacherDto.setFirstName(null);
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifFirstNameNotMatchPattern() throws Exception {
        teacherDto.setId(null);
        teacherDto.setFirstName("firstName$~");
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifSecondNameIsNull() throws Exception {
        teacherDto.setId(null);
        teacherDto.setSecondName(null);
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifSecondNameNotMatchPattern() throws Exception {
        teacherDto.setId(null);
        teacherDto.setSecondName("firstName$~");
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifEmailIsNullOrEmpty() throws Exception {
        teacherDto.setId(null);
        teacherDto.setEmail("");
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifEmailIsInvalid() throws Exception {
        teacherDto.setId(null);
        teacherDto.setEmail("helloWorld");
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifPasswordIsNullOrEmpty() throws Exception {
        teacherDto.setId(null);
        teacherDto.setPassword("");
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifPasswordNotMatchLength() throws Exception {
        teacherDto.setId(null);
        teacherDto.setPassword("length");
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifDateBirthIsInFuture() throws Exception {
        teacherDto.setId(null);
        teacherDto.setDateBirth(LocalDate.now().plusMonths(1));
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifEducationIsNull() throws Exception {
        teacherDto.setId(null);
        teacherDto.setEducation(null);
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifEducationNotMatchPattern() throws Exception {
        teacherDto.setId(null);
        teacherDto.setEducation("education$~");
        mockMvc.perform(post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return200_ifInputsIsValid() throws Exception {
        teacherDto.setRoles(Collections.singleton(new Role()));
        mockMvc.perform(put("/teachers/{id}", teacherDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isOk());
        Mockito.verify(teacherService, Mockito.times(1)).update(teacherDto);
    }

    @Test
    public void update_return404_ifIdNotExist() throws Exception {
        teacherDto.setRoles(Collections.singleton(new Role()));
        teacherDto.setId(Long.MAX_VALUE);
        Mockito.when(teacherService.update(teacherDto)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(put("/teachers/{id}", teacherDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_return400_ifIdIsNull() throws Exception {
        teacherDto.setRoles(Collections.singleton(new Role()));
        teacherDto.setId(null);
        mockMvc.perform(put("/teachers/null")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifFirstNameIsNull() throws Exception {
        teacherDto.setRoles(Collections.singleton(new Role()));
        teacherDto.setFirstName(null);
        mockMvc.perform(put("/teachers/{id}", teacherDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifFirstNameNotMatchPattern() throws Exception {
        teacherDto.setRoles(Collections.singleton(new Role()));
        teacherDto.setFirstName("firstName$~");
        mockMvc.perform(put("/teachers/{id}", teacherDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifSecondNameIsNull() throws Exception {
        teacherDto.setRoles(Collections.singleton(new Role()));
        teacherDto.setSecondName(null);
        mockMvc.perform(put("/teachers/{id}", teacherDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifSecondNameNotMatchPattern() throws Exception {
        teacherDto.setRoles(Collections.singleton(new Role()));
        teacherDto.setSecondName("secondName$~");
        mockMvc.perform(put("/teachers/{id}", teacherDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifDateBirthIsInFuture() throws Exception {
        teacherDto.setRoles(Collections.singleton(new Role()));
        teacherDto.setDateBirth(LocalDate.now().plusMonths(1));
        mockMvc.perform(put("/teachers/{id}", teacherDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifRolesIsNull() throws Exception {
        mockMvc.perform(put("/teachers/{id}", teacherDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifEducationIsNull() throws Exception {
        teacherDto.setId(null);
        teacherDto.setEducation(null);
        mockMvc.perform(post("/teachers/{id}", teacherDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifEducationNotMatchPattern() throws Exception {
        teacherDto.setId(null);
        teacherDto.setEducation("education$~");
        mockMvc.perform(post("/teachers/{id}", teacherDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAll_return200() throws Exception {
        mockMvc.perform(get("/teachers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(teacherService, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(get("/teachers/{id}", teacherDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(teacherService, Mockito.times(1)).findById(teacherDto.getId());
    }

    @Test
    public void findById_return404_ifInputIdNotExist() throws Exception {
        Mockito.when(teacherService.findById(Long.MAX_VALUE)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/teachers/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteById_return200_ifInputsValid() throws Exception {
        mockMvc.perform(delete("/teachers/{id}", teacherDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(teacherService, Mockito.times(1)).deleteById(teacherDto.getId());
    }

    @Test
    public void deleteById_return404_ifInputIdNotExist() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(teacherService).deleteById(Long.MAX_VALUE);
        mockMvc.perform(delete("/teachers/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
