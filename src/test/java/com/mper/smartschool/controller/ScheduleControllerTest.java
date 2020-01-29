package com.mper.smartschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.ScheduleDto;
import com.mper.smartschool.service.ScheduleService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest {

    @MockBean
    private ScheduleService scheduleService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ScheduleDto scheduleDto;

    @BeforeEach
    void setUp() {
        scheduleDto = DtoDirector.makeTestScheduleDtoById(1L);
    }

    @Test
    public void create_return201_ifInputsIsValid() throws Exception {
        scheduleDto.setId(null);
        mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isCreated());
        Mockito.verify(scheduleService, Mockito.times(1)).create(scheduleDto);
    }

    @Test
    public void create_return400_ifIdIsNotNull() throws Exception {
        mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifDateIsInPast() throws Exception {
        scheduleDto.setId(null);
        scheduleDto.setDate(LocalDate.now().minusMonths(1));
        mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifLessonNumberIsFewerThenMin() throws Exception {
        scheduleDto.setId(null);
        scheduleDto.setLessonNumber(0);
        mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifLessonNumberIsMoreThenMax() throws Exception {
        scheduleDto.setId(null);
        scheduleDto.setLessonNumber(11);
        mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifSchoolClassIsNull() throws Exception {
        scheduleDto.setId(null);
        scheduleDto.setSchoolClass(null);
        mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifTeachersSubjectIsNull() throws Exception {
        scheduleDto.setId(null);
        scheduleDto.setTeachersSubject(null);
        mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(put("/schedules/{id}", scheduleDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isOk());
        Mockito.verify(scheduleService, Mockito.times(1)).update(scheduleDto);
    }

    @Test
    public void update_return404_ifIdNotExist() throws Exception {
        scheduleDto.setId(Long.MAX_VALUE);
        Mockito.when(scheduleService.update(scheduleDto)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(put("/schedules/{id}", scheduleDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_return400_ifIdIsNull() throws Exception {
        mockMvc.perform(put("/schedules/null")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifLessonNumberIsFewerThenMin() throws Exception {
        scheduleDto.setId(null);
        scheduleDto.setLessonNumber(0);
        mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifLessonNumberIsMoreThenMax() throws Exception {
        scheduleDto.setId(null);
        scheduleDto.setLessonNumber(11);
        mockMvc.perform(post("/schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifTeachersSubjectIsNull() throws Exception {
        scheduleDto.setTeachersSubject(null);
        mockMvc.perform(put("/schedules/{id}", scheduleDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scheduleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAll_return200() throws Exception {
        mockMvc.perform(get("/schedules")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(scheduleService, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(get("/schedules/{id}", scheduleDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(scheduleService, Mockito.times(1))
                .findById(scheduleDto.getId());
    }

    @Test
    public void findById_return404_ifInputIdNotExist() throws Exception {
        Mockito.when(scheduleService.findById(Long.MAX_VALUE)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/schedules/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteById_return200_ifInputsValid() throws Exception {
        mockMvc.perform(delete("/schedules/{id}", scheduleDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(scheduleService, Mockito.times(1))
                .deleteById(scheduleDto.getId());
    }

    @Test
    public void deleteById_return404_ifInputIdNotExist() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(scheduleService).deleteById(Long.MAX_VALUE);
        mockMvc.perform(delete("/schedules/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
