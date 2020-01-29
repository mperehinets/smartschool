package com.mper.smartschool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mper.smartschool.DtoDirector;
import com.mper.smartschool.dto.SignedPersonDto;
import com.mper.smartschool.service.SignedPersonService;
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

@WebMvcTest(SignedPersonController.class)
class SignedPersonControllerTest {

    @MockBean
    private SignedPersonService signedPersonService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SignedPersonDto signedPersonDto;

    @BeforeEach
    void setUp() {
        signedPersonDto = DtoDirector.makeTestSignedPersonDtoById(1L);
    }

    @Test
    public void create_return201_ifInputsIsValid() throws Exception {
        signedPersonDto.setId(null);
        mockMvc.perform(post("/signedPersons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isCreated());
        Mockito.verify(signedPersonService, Mockito.times(1)).create(signedPersonDto);
    }

    @Test
    public void create_return400_ifIdIsNotNull() throws Exception {
        mockMvc.perform(post("/signedPersons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifFullNameIsNull() throws Exception {
        signedPersonDto.setId(null);
        signedPersonDto.setFullName(null);
        mockMvc.perform(post("/signedPersons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifFullNameNotMatchPattern() throws Exception {
        signedPersonDto.setId(null);
        signedPersonDto.setFullName("fullName$~");
        mockMvc.perform(post("/signedPersons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifEmailIsNullOrEmpty() throws Exception {
        signedPersonDto.setId(null);
        signedPersonDto.setEmail("");
        mockMvc.perform(post("/signedPersons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_return400_ifEmailIsInvalid() throws Exception {
        signedPersonDto.setId(null);
        signedPersonDto.setEmail("helloWorld");
        mockMvc.perform(post("/signedPersons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(put("/signedPersons/{id}", signedPersonDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isOk());
        Mockito.verify(signedPersonService, Mockito.times(1)).update(signedPersonDto);
    }

    @Test
    public void update_return404_ifIdNotExist() throws Exception {
        signedPersonDto.setId(Long.MAX_VALUE);
        Mockito.when(signedPersonService.update(signedPersonDto)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(put("/signedPersons/{id}", signedPersonDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_return400_ifIdIsNull() throws Exception {
        mockMvc.perform(put("/signedPersons/null")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifFullNameIsNull() throws Exception {
        signedPersonDto.setFullName(null);
        mockMvc.perform(put("/signedPersons/{id}", signedPersonDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifFullNameNotMatchPattern() throws Exception {
        signedPersonDto.setFullName("fullName$~");
        mockMvc.perform(put("/signedPersons/{id}", signedPersonDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifEmailIsNullOrEmpty() throws Exception {
        signedPersonDto.setEmail("");
        mockMvc.perform(put("/signedPersons/{id}", signedPersonDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_return400_ifEmailIsInvalid() throws Exception {
        signedPersonDto.setEmail("helloWorld");
        mockMvc.perform(put("/signedPersons/{id}", signedPersonDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signedPersonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAll_return200() throws Exception {
        mockMvc.perform(get("/signedPersons")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(signedPersonService, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_return200_ifInputsIsValid() throws Exception {
        mockMvc.perform(get("/signedPersons/{id}", signedPersonDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(signedPersonService, Mockito.times(1)).findById(signedPersonDto.getId());
    }

    @Test
    public void findById_return404_ifInputIdNotExist() throws Exception {
        Mockito.when(signedPersonService.findById(Long.MAX_VALUE)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/signedPersons/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteById_return200_ifInputsValid() throws Exception {
        mockMvc.perform(delete("/signedPersons/{id}", signedPersonDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(signedPersonService, Mockito.times(1))
                .deleteById(signedPersonDto.getId());
    }

    @Test
    public void deleteById_return404_ifInputIdNotExist() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(signedPersonService).deleteById(Long.MAX_VALUE);
        mockMvc.perform(delete("/signedPersons/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
