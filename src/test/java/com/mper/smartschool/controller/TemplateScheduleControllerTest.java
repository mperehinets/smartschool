package com.mper.smartschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.TemplateScheduleDto;
import com.mper.smartschool.exception.DayFilledByLessonsException;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.service.TemplateScheduleService;
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

@WebMvcTest(TemplateScheduleController.class)
@ActiveProfiles("test")
class TemplateScheduleControllerTest {

    @MockBean
    private TemplateScheduleService templateScheduleService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TemplateScheduleDto templateScheduleDto;

    @BeforeEach
    void setUp() {
        templateScheduleDto = DtoDirector.makeTestTemplateScheduleDtoById(1L);
    }

    @Test
    public void create_return201_ifInputsIsValid() throws Exception {
        templateScheduleDto.setId(null);
        mockMvc.perform(post("/smartschool/templates-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isCreated());
        Mockito.verify(templateScheduleService, Mockito.times(1)).create(templateScheduleDto);
    }

    @Test
    public void create_return400_ifIdIsNotNull() throws Exception {
        mockMvc.perform(post("/smartschool/templates-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifClassNumberIsFewerThenMin() throws Exception {
        templateScheduleDto.setId(null);
        templateScheduleDto.setClassNumber(0);
        mockMvc.perform(post("/smartschool/templates-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifClassNumberIsMoreThenMax() throws Exception {
        templateScheduleDto.setId(null);
        templateScheduleDto.setClassNumber(12);
        mockMvc.perform(post("/smartschool/templates-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifLessonNumberIsFewerThenMin() throws Exception {
        templateScheduleDto.setId(null);
        templateScheduleDto.setLessonNumber(0);
        mockMvc.perform(post("/smartschool/templates-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifLessonNumberIsMoreThenMax() throws Exception {
        templateScheduleDto.setId(null);
        templateScheduleDto.setLessonNumber(11);
        mockMvc.perform(post("/smartschool/templates-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifSubjectIsNull() throws Exception {
        templateScheduleDto.setId(null);
        templateScheduleDto.setSubject(null);
        mockMvc.perform(post("/smartschool/templates-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return403_ifDayFilledByLessons() throws Exception {
        templateScheduleDto.setId(null);
        Mockito.when(templateScheduleService.create(templateScheduleDto))
                .thenThrow(new DayFilledByLessonsException(templateScheduleDto.getDayOfWeek()));
        mockMvc.perform(post("/smartschool/templates-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void update_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(put("/smartschool/templates-schedule/{id}", templateScheduleDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isOk());
        Mockito.verify(templateScheduleService, Mockito.times(1)).update(templateScheduleDto);
    }

    @Test
    public void update_return404_ifIdNotExist() throws Exception {
        templateScheduleDto.setId(Long.MAX_VALUE);
        Mockito.when(templateScheduleService.update(templateScheduleDto)).thenThrow(new NotFoundException("", null));
        mockMvc.perform(put("/smartschool/templates-schedule/{id}", templateScheduleDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_return400_ifIdIsNull() throws Exception {
        mockMvc.perform(put("/smartschool/templates-schedule/null")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifLessonNumberIsFewerThenMin() throws Exception {
        templateScheduleDto.setId(null);
        templateScheduleDto.setLessonNumber(0);
        mockMvc.perform(post("/smartschool/templates-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifLessonNumberIsMoreThenMax() throws Exception {
        templateScheduleDto.setId(null);
        templateScheduleDto.setLessonNumber(11);
        mockMvc.perform(post("/smartschool/templates-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifSubjectIsNull() throws Exception {
        templateScheduleDto.setSubject(null);
        mockMvc.perform(put("/smartschool/templates-schedule/{id}", templateScheduleDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(templateScheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAll_return200() throws Exception {
        mockMvc.perform(get("/smartschool/templates-schedule")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(templateScheduleService, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(get("/smartschool/templates-schedule/{id}", templateScheduleDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(templateScheduleService, Mockito.times(1))
                .findById(templateScheduleDto.getId());
    }

    @Test
    public void findById_return404_ifInputIdNotExist() throws Exception {
        Mockito.when(templateScheduleService.findById(Long.MAX_VALUE)).thenThrow(new NotFoundException("", null));
        mockMvc.perform(get("/smartschool/templates-schedule/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteById_return200_ifInputsValid() throws Exception {
        mockMvc.perform(delete("/smartschool/templates-schedule/{id}", templateScheduleDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(templateScheduleService, Mockito.times(1))
                .deleteById(templateScheduleDto.getId());
    }

    @Test
    public void deleteById_return404_ifInputIdNotExist() throws Exception {
        Mockito.doThrow(new NotFoundException("", null)).when(templateScheduleService).deleteById(Long.MAX_VALUE);
        mockMvc.perform(delete("/smartschool/templates-schedule/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
