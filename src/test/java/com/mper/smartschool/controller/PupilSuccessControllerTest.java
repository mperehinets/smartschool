package com.mper.smartschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.PupilSuccessDto;
import com.mper.smartschool.exception.NotFoundException;
import com.mper.smartschool.service.PupilSuccessService;
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

@WebMvcTest(PupilSuccessController.class)
@ActiveProfiles("test")
class PupilSuccessControllerTest {

    @MockBean
    private PupilSuccessService pupilSuccessService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PupilSuccessDto pupilSuccessDto;

    @BeforeEach
    void setUp() {
        pupilSuccessDto = DtoDirector.makeTestPupilSuccessDtoById(1L);
    }

    @Test
    public void create_return201_ifInputsIsValid() throws Exception {
        pupilSuccessDto.setId(null);
        mockMvc.perform(post("/smartschool/pupilSuccesses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilSuccessDto)))
                .andExpect(status().isCreated());
        Mockito.verify(pupilSuccessService, Mockito.times(1)).create(pupilSuccessDto);
    }

    @Test
    public void create_return400_ifIdIsNotNull() throws Exception {
        mockMvc.perform(post("/smartschool/pupilSuccesses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilSuccessDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifPupilIsNull() throws Exception {
        pupilSuccessDto.setId(null);
        pupilSuccessDto.setPupil(null);
        mockMvc.perform(post("/smartschool/pupilSuccesses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilSuccessDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifScheduleIsNull() throws Exception {
        pupilSuccessDto.setId(null);
        pupilSuccessDto.setSchedule(null);
        mockMvc.perform(post("/smartschool/pupilSuccesses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilSuccessDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifRatingIsFewerThenMin() throws Exception {
        pupilSuccessDto.setId(null);
        pupilSuccessDto.setRating(0);
        mockMvc.perform(post("/smartschool/pupilSuccesses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilSuccessDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifRatingIsMoreThenMax() throws Exception {
        pupilSuccessDto.setId(null);
        pupilSuccessDto.setRating(13);
        mockMvc.perform(post("/smartschool/pupilSuccesses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilSuccessDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(put("/smartschool/pupilSuccesses/{id}", pupilSuccessDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilSuccessDto)))
                .andExpect(status().isOk());
        Mockito.verify(pupilSuccessService, Mockito.times(1)).update(pupilSuccessDto);
    }

    @Test
    public void update_return404_ifIdNotExist() throws Exception {
        pupilSuccessDto.setId(Long.MAX_VALUE);
        Mockito.when(pupilSuccessService.update(pupilSuccessDto)).thenThrow(new NotFoundException("", null));
        mockMvc.perform(put("/smartschool/pupilSuccesses/{id}", pupilSuccessDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilSuccessDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_return400_ifIdIsNull() throws Exception {
        mockMvc.perform(put("/smartschool/pupilSuccesses/null")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilSuccessDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifPupilIsNull() throws Exception {
        pupilSuccessDto.setPupil(null);
        mockMvc.perform(put("/smartschool/pupilSuccesses/{id}", pupilSuccessDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilSuccessDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifRatingIsFewerThenMin() throws Exception {
        pupilSuccessDto.setId(null);
        pupilSuccessDto.setRating(0);
        mockMvc.perform(post("/smartschool/pupilSuccesses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilSuccessDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifRatingIsMoreThenMax() throws Exception {
        pupilSuccessDto.setId(null);
        pupilSuccessDto.setRating(13);
        mockMvc.perform(post("/smartschool/pupilSuccesses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pupilSuccessDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAll_return200() throws Exception {
        mockMvc.perform(get("/smartschool/pupilSuccesses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(pupilSuccessService, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(get("/smartschool/pupilSuccesses/{id}", pupilSuccessDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(pupilSuccessService, Mockito.times(1))
                .findById(pupilSuccessDto.getId());
    }

    @Test
    public void findById_return404_ifInputIdNotExist() throws Exception {
        Mockito.when(pupilSuccessService.findById(Long.MAX_VALUE)).thenThrow(new NotFoundException("", null));
        mockMvc.perform(get("/smartschool/pupilSuccesses/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteById_return200_ifInputsValid() throws Exception {
        mockMvc.perform(delete("/smartschool/pupilSuccesses/{id}", pupilSuccessDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(pupilSuccessService, Mockito.times(1))
                .deleteById(pupilSuccessDto.getId());
    }

    @Test
    public void deleteById_return404_ifInputIdNotExist() throws Exception {
        Mockito.doThrow(new NotFoundException("", null)).when(pupilSuccessService).deleteById(Long.MAX_VALUE);
        mockMvc.perform(delete("/smartschool/pupilSuccesses/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
