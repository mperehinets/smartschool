package com.mper.smartschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.SchoolClassDto;
import com.mper.smartschool.service.SchoolClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SchoolClassController.class)
class SchoolClassControllerTest {

    @MockBean
    private SchoolClassService schoolClassService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SchoolClassDto schoolClassDto;

    @BeforeEach
    void setUp() {
        schoolClassDto = DtoDirector.makeTestSchoolClassDtoById(1L);
    }

    @Test
    public void create_return201_ifInputsIsValid() throws Exception {
        schoolClassDto.setId(null);
        mockMvc.perform(post("/schoolClasses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schoolClassDto)))
                .andExpect(status().isCreated());
        Mockito.verify(schoolClassService, Mockito.times(1)).create(schoolClassDto);
    }

    @Test
    public void create_return400_ifIdIsNotNull() throws Exception {
        mockMvc.perform(post("/schoolClasses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schoolClassDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifNumberIsFewerThenMin() throws Exception {
        schoolClassDto.setId(null);
        schoolClassDto.setNumber(0);
        mockMvc.perform(post("/schoolClasses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schoolClassDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifNumberIsMoreThenMax() throws Exception {
        schoolClassDto.setId(null);
        schoolClassDto.setNumber(12);
        mockMvc.perform(post("/schoolClasses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schoolClassDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifClassTeacherIsNull() throws Exception {
        schoolClassDto.setId(null);
        schoolClassDto.setClassTeacher(null);
        mockMvc.perform(post("/schoolClasses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schoolClassDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(put("/schoolClasses/{id}", schoolClassDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schoolClassDto)))
                .andExpect(status().isOk());
        Mockito.verify(schoolClassService, Mockito.times(1)).update(schoolClassDto);
    }

    @Test
    public void update_return404_ifIdNotExist() throws Exception {
        schoolClassDto.setId(Long.MAX_VALUE);
        Mockito.when(schoolClassService.update(schoolClassDto)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(put("/schoolClasses/{id}", schoolClassDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schoolClassDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_return400_ifIdIsNull() throws Exception {
        mockMvc.perform(put("/schoolClasses/null")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schoolClassDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifClassTeacherIsNull() throws Exception {
        schoolClassDto.setClassTeacher(null);
        mockMvc.perform(put("/schoolClasses/{id}", schoolClassDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schoolClassDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAll_return200() throws Exception {
        mockMvc.perform(get("/schoolClasses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(schoolClassService, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(get("/schoolClasses/{id}", schoolClassDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(schoolClassService, Mockito.times(1)).findById(schoolClassDto.getId());
    }

    @Test
    public void findById_return404_ifInputIdNotExist() throws Exception {
        Mockito.when(schoolClassService.findById(Long.MAX_VALUE)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/schoolClasses/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteById_return200_ifInputsValid() throws Exception {
        mockMvc.perform(delete("/schoolClasses/{id}", schoolClassDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(schoolClassService, Mockito.times(1))
                .deleteById(schoolClassDto.getId());
    }

    @Test
    public void deleteById_return404_ifInputIdNotExist() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(schoolClassService).deleteById(Long.MAX_VALUE);
        mockMvc.perform(delete("/schoolClasses/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
