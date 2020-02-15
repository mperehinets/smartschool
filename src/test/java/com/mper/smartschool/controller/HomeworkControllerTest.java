package com.mper.smartschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.HomeworkDto;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.service.HomeworkService;
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

@WebMvcTest(HomeworkController.class)
@ActiveProfiles("test")
class HomeworkControllerTest {

    @MockBean
    private HomeworkService homeworkService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private HomeworkDto homeworkDto;

    @BeforeEach
    void setUp() {
        homeworkDto = DtoDirector.makeTestHomeworkDtoById(1L);
    }

    @Test
    public void create_return201_ifInputsIsValid() throws Exception {
        homeworkDto.setId(null);
        mockMvc.perform(post("/smartschool/homeworks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(homeworkDto)))
                .andExpect(status().isCreated());
        Mockito.verify(homeworkService, Mockito.times(1)).create(homeworkDto);
    }

    @Test
    public void create_return400_ifIdIsNotNull() throws Exception {
        mockMvc.perform(post("/smartschool/homeworks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(homeworkDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifScheduleIsNull() throws Exception {
        homeworkDto.setId(null);
        homeworkDto.setSchedule(null);
        mockMvc.perform(post("/smartschool/homeworks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(homeworkDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifHomeworkIsNull() throws Exception {
        homeworkDto.setId(null);
        homeworkDto.setHomework(null);
        mockMvc.perform(post("/smartschool/homeworks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(homeworkDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifHomeworkNotMatchPattern() throws Exception {
        homeworkDto.setId(null);
        homeworkDto.setHomework("homework$~");
        mockMvc.perform(post("/smartschool/homeworks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(homeworkDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(put("/smartschool/homeworks/{id}", homeworkDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(homeworkDto)))
                .andExpect(status().isOk());
        Mockito.verify(homeworkService, Mockito.times(1)).update(homeworkDto);
    }

    @Test
    public void update_return404_ifIdNotExist() throws Exception {
        homeworkDto.setId(Long.MAX_VALUE);
        Mockito.when(homeworkService.update(homeworkDto)).thenThrow(new NotFoundException("", null));
        mockMvc.perform(put("/smartschool/homeworks/{id}", homeworkDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(homeworkDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_return400_ifIdIsNull() throws Exception {
        mockMvc.perform(put("/smartschool/homeworks/null")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(homeworkDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifHomeworkIsNull() throws Exception {
        homeworkDto.setId(null);
        homeworkDto.setHomework(null);
        mockMvc.perform(post("/smartschool/homeworks/{id}", homeworkDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(homeworkDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifHomeworkNotMatchPattern() throws Exception {
        homeworkDto.setId(null);
        homeworkDto.setHomework("homework$~");
        mockMvc.perform(post("/smartschool/homeworks/{id}", homeworkDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(homeworkDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAll_return200() throws Exception {
        mockMvc.perform(get("/smartschool/homeworks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(homeworkService, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(get("/smartschool/homeworks/{id}", homeworkDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(homeworkService, Mockito.times(1))
                .findById(homeworkDto.getId());
    }

    @Test
    public void findById_return404_ifInputIdNotExist() throws Exception {
        Mockito.when(homeworkService.findById(Long.MAX_VALUE)).thenThrow(new NotFoundException("", null));
        mockMvc.perform(get("/smartschool/homeworks/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteById_return200_ifInputsValid() throws Exception {
        mockMvc.perform(delete("/smartschool/homeworks/{id}", homeworkDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(homeworkService, Mockito.times(1)).deleteById(homeworkDto.getId());
    }

    @Test
    public void deleteById_return404_ifInputIdNotExist() throws Exception {
        Mockito.doThrow(new NotFoundException("", null)).when(homeworkService).deleteById(Long.MAX_VALUE);
        mockMvc.perform(delete("/smartschool/homeworks/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
