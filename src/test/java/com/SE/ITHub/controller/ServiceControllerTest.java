package com.SE.ITHub.controller;

import com.SE.ITHub.dto.ServiceResponseDto;
import com.SE.ITHub.service.ServicesServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServicesController.class)
public class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServicesServiceImpl servicesService;

    @Test
    void getServiceById_ReturnsOk() throws Exception {
        UUID id = UUID.randomUUID();
        ServiceResponseDto response = ServiceResponseDto.builder().id(id).title("Test").build();

        when(servicesService.getServiceById(id)).thenReturn(response);

        mockMvc.perform(get("/api/services/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

}