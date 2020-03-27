package com.mper.smartschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.ChangeStatusDto;
import com.mper.smartschool.dto.SubjectDto;
import com.mper.smartschool.entity.modelsEnum.EntityStatus;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.service.SubjectService;
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

@WebMvcTest(SubjectController.class)
@ActiveProfiles("test")
class SubjectControllerTest {

    @MockBean
    private SubjectService subjectService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SubjectDto subjectDto;

    @BeforeEach
    void setUp() {
        subjectDto = DtoDirector.makeTestSubjectDtoById(1L);
    }

    @Test
    public void create_return201_ifInputsIsValid() throws Exception {
        subjectDto.setId(null);
        mockMvc.perform(post("/smartschool/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectDto)))
                .andExpect(status().isCreated());
        Mockito.verify(subjectService, Mockito.times(1)).create(subjectDto);
    }

    @Test
    public void create_return400_ifIdIsNotNull() throws Exception {
        mockMvc.perform(post("/smartschool/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifNameIsNull() throws Exception {
        subjectDto.setId(null);
        subjectDto.setName(null);
        mockMvc.perform(post("/smartschool/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifNameNotMatchPattern() throws Exception {
        subjectDto.setId(null);
        subjectDto.setName("name$~");
        mockMvc.perform(post("/smartschool/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(put("/smartschool/subjects/{id}", subjectDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectDto)))
                .andExpect(status().isOk());
        Mockito.verify(subjectService, Mockito.times(1)).update(subjectDto);
    }

    @Test
    public void update_return404_ifIdNotExist() throws Exception {
        subjectDto.setId(Long.MAX_VALUE);
        Mockito.when(subjectService.update(subjectDto)).thenThrow(new NotFoundException("", null));
        mockMvc.perform(put("/smartschool/subjects/{id}", subjectDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_return400_ifIdIsNull() throws Exception {
        mockMvc.perform(put("/smartschool/subjects/null")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifNameIsNull() throws Exception {
        subjectDto.setName(null);
        mockMvc.perform(put("/smartschool/subjects/{id}", subjectDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifNameNotMatchPattern() throws Exception {
        subjectDto.setName("name$~");
        mockMvc.perform(put("/smartschool/subjects/{id}", subjectDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAll_return200() throws Exception {
        mockMvc.perform(get("/smartschool/subjects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(subjectService, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(get("/smartschool/subjects/{id}", subjectDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(subjectService, Mockito.times(1)).findById(subjectDto.getId());
    }

    @Test
    public void findById_return404_ifInputIdNotExist() throws Exception {
        Mockito.when(subjectService.findById(Long.MAX_VALUE)).thenThrow(new NotFoundException("", null));
        mockMvc.perform(get("/smartschool/subjects/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void changeStatusById_return200_ifInputsValid() throws Exception {
        ChangeStatusDto changeStatusDto = ChangeStatusDto.builder()
                .id(subjectDto.getId())
                .newStatus(EntityStatus.EXCLUDED)
                .build();

        mockMvc.perform(put("/smartschool/subjects/change-status/{id}", changeStatusDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeStatusDto)))
                .andExpect(status().isOk());
        Mockito.verify(subjectService, Mockito.times(1))
                .changeStatusById(changeStatusDto);
    }

    @Test
    public void changeStatusById_return404_ifInputIdNotExist() throws Exception {
        ChangeStatusDto changeStatusDto = ChangeStatusDto.builder()
                .id(Long.MAX_VALUE)
                .newStatus(EntityStatus.EXCLUDED)
                .build();
        Mockito.doThrow(new NotFoundException("", null))
                .when(subjectService).changeStatusById(changeStatusDto);
        mockMvc.perform(put("/smartschool/subjects/change-status/{id}", changeStatusDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeStatusDto)))
                .andExpect(status().isNotFound());
    }
}
