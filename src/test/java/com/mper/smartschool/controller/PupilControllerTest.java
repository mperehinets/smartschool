package com.mper.smartschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.PupilDto;
import com.mper.smartschool.entity.Role;
import com.mper.smartschool.service.PupilService;
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

@WebMvcTest(PupilController.class)
class PupilControllerTest {

    @MockBean
    private PupilService pupilService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PupilDto pupilDto;

    @BeforeEach
    void setUp() {
        pupilDto = DtoDirector.makeTestPupilDtoById(1L);
    }

    @Test
    public void create_return201_ifInputsIsValid() throws Exception {
        pupilDto.setId(null);
        mockMvc.perform(post("/pupils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isCreated());
        Mockito.verify(pupilService, Mockito.times(1)).create(pupilDto);
    }

    @Test
    public void create_return400_ifIdIsNotNull() throws Exception {
        mockMvc.perform(post("/pupils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifFirstNameIsNull() throws Exception {
        pupilDto.setId(null);
        pupilDto.setFirstName(null);
        mockMvc.perform(post("/pupils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifFirstNameNotMatchPattern() throws Exception {
        pupilDto.setId(null);
        pupilDto.setFirstName("firstName$~");
        mockMvc.perform(post("/pupils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifSecondNameIsNull() throws Exception {
        pupilDto.setId(null);
        pupilDto.setSecondName(null);
        mockMvc.perform(post("/pupils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifSecondNameNotMatchPattern() throws Exception {
        pupilDto.setId(null);
        pupilDto.setSecondName("secondName$~");
        mockMvc.perform(post("/pupils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifEmailIsNullOrEmpty() throws Exception {
        pupilDto.setId(null);
        pupilDto.setEmail("");
        mockMvc.perform(post("/pupils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifEmailIsInvalid() throws Exception {
        pupilDto.setId(null);
        pupilDto.setEmail("helloWorld");
        mockMvc.perform(post("/pupils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifPasswordIsNullOrEmpty() throws Exception {
        pupilDto.setId(null);
        pupilDto.setPassword("");
        mockMvc.perform(post("/pupils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifPasswordNotMatchLength() throws Exception {
        pupilDto.setId(null);
        pupilDto.setPassword("length");
        mockMvc.perform(post("/pupils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifDateBirthIsInFuture() throws Exception {
        pupilDto.setId(null);
        pupilDto.setDateBirth(LocalDate.now().plusMonths(1));
        mockMvc.perform(post("/pupils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifSchoolClassIsNull() throws Exception {
        pupilDto.setId(null);
        pupilDto.setSchoolClass(null);
        mockMvc.perform(post("/pupils")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return200_ifInputsIsValid() throws Exception {
        pupilDto.setRoles(Collections.singleton(new Role()));
        mockMvc.perform(put("/pupils/{id}", pupilDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isOk());
        Mockito.verify(pupilService, Mockito.times(1)).update(pupilDto);
    }

    @Test
    public void update_return404_ifIdNotExist() throws Exception {
        pupilDto.setRoles(Collections.singleton(new Role()));
        pupilDto.setId(Long.MAX_VALUE);
        Mockito.when(pupilService.update(pupilDto)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(put("/pupils/{id}", pupilDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_return400_ifIdIsNull() throws Exception {
        pupilDto.setRoles(Collections.singleton(new Role()));
        pupilDto.setId(null);
        mockMvc.perform(put("/pupils/null")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifFirstNameIsNull() throws Exception {
        pupilDto.setRoles(Collections.singleton(new Role()));
        pupilDto.setFirstName(null);
        mockMvc.perform(put("/pupils/{id}", pupilDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifFirstNameNotMatchPattern() throws Exception {
        pupilDto.setRoles(Collections.singleton(new Role()));
        pupilDto.setFirstName("firstName$~");
        mockMvc.perform(put("/pupils/{id}", pupilDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifSecondNameIsNull() throws Exception {
        pupilDto.setRoles(Collections.singleton(new Role()));
        pupilDto.setSecondName(null);
        mockMvc.perform(put("/pupils/{id}", pupilDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifSecondNameNotMatchPattern() throws Exception {
        pupilDto.setRoles(Collections.singleton(new Role()));
        pupilDto.setSecondName("secondName$~");
        mockMvc.perform(put("/pupils/{id}", pupilDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifDateBirthIsInFuture() throws Exception {
        pupilDto.setRoles(Collections.singleton(new Role()));
        pupilDto.setDateBirth(LocalDate.now().plusMonths(1));
        mockMvc.perform(put("/pupils/{id}", pupilDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifRolesIsNull() throws Exception {
        mockMvc.perform(put("/pupils/{id}", pupilDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifSchoolClassIsNull() throws Exception {
        pupilDto.setRoles(Collections.singleton(new Role()));
        pupilDto.setSchoolClass(null);
        mockMvc.perform(put("/pupils/{id}", pupilDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAll_return200() throws Exception {
        mockMvc.perform(get("/pupils")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(pupilService, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(get("/pupils/{id}", pupilDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(pupilService, Mockito.times(1)).findById(pupilDto.getId());
    }

    @Test
    public void findById_return404_ifInputIdNotExist() throws Exception {
        Mockito.when(pupilService.findById(Long.MAX_VALUE)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/pupils/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteById_return200_ifInputsValid() throws Exception {
        mockMvc.perform(delete("/pupils/{id}", pupilDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(pupilService, Mockito.times(1)).deleteById(pupilDto.getId());
    }

    @Test
    public void deleteById_return404_ifInputIdNotExist() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(pupilService).deleteById(Long.MAX_VALUE);
        mockMvc.perform(delete("/pupils/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
