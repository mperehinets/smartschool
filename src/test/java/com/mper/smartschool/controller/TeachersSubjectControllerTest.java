package com.mper.smartschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.TeachersSubjectDto;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.service.TeachersSubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeachersSubjectController.class)
@ActiveProfiles("test")
class TeachersSubjectControllerTest {

    @MockBean
    private TeachersSubjectService teachersSubjectService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TeachersSubjectDto teachersSubjectDto;

    @BeforeEach
    void setUp() {
        teachersSubjectDto = DtoDirector.makeTestTeachersSubjectDtoById(1L);
    }

    @Test
    public void create_return201_ifInputsIsValid() throws Exception {
        teachersSubjectDto.setId(null);
        mockMvc.perform(post("/smartschool/teachersSubjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teachersSubjectDto)))
                .andExpect(status().isCreated());
        Mockito.verify(teachersSubjectService, Mockito.times(1)).create(teachersSubjectDto);
    }

    @Test
    public void create_return400_ifIdIsNotNull() throws Exception {
        mockMvc.perform(post("/smartschool/teachersSubjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teachersSubjectDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifTeacherIsNull() throws Exception {
        teachersSubjectDto.setId(null);
        teachersSubjectDto.setTeacher(null);
        mockMvc.perform(post("/smartschool/teachersSubjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teachersSubjectDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifSubjectIsNull() throws Exception {
        teachersSubjectDto.setId(null);
        teachersSubjectDto.setSubject(null);
        mockMvc.perform(post("/smartschool/teachersSubjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teachersSubjectDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAll_return200() throws Exception {
        mockMvc.perform(get("/smartschool/teachersSubjects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(teachersSubjectService, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(get("/smartschool/teachersSubjects/{id}", teachersSubjectDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(teachersSubjectService, Mockito.times(1))
                .findById(teachersSubjectDto.getId());
    }

    @Test
    public void findById_return404_ifInputIdNotExist() throws Exception {
        Mockito.when(teachersSubjectService.findById(Long.MAX_VALUE)).thenThrow(new NotFoundException("", null));
        mockMvc.perform(get("/smartschool/teachersSubjects/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteById_return200_ifInputsValid() throws Exception {
        mockMvc.perform(delete("/smartschool/teachersSubjects/{id}", teachersSubjectDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(teachersSubjectService, Mockito.times(1))
                .deleteById(teachersSubjectDto.getId());
    }

    @Test
    public void deleteById_return404_ifInputIdNotExist() throws Exception {
        Mockito.doThrow(new NotFoundException("", null)).when(teachersSubjectService).deleteById(Long.MAX_VALUE);
        mockMvc.perform(delete("/smartschool/teachersSubjects/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void stopTeachSubjectById_return200_ifInputsValid() throws Exception {
        mockMvc.perform(put("/smartschool/teachersSubjects/stopTeachSubject/{id}", teachersSubjectDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(teachersSubjectService, Mockito.times(1))
                .stopTeachSubjectById(teachersSubjectDto.getId());
    }

    @Test
    public void stopTeachSubjectById_return404_ifInputIdNotExist() throws Exception {
        Mockito.doThrow(new NotFoundException("", null)).when(teachersSubjectService)
                .stopTeachSubjectById(Long.MAX_VALUE);
        mockMvc.perform(put("/smartschool/teachersSubjects/stopTeachSubject/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
