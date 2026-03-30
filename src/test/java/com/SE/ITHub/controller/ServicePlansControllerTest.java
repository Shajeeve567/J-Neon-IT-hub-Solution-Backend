package com.SE.ITHub.controller;

import com.SE.ITHub.dto.PlansUpdateReqDto;
import com.SE.ITHub.dto.ServicePlanReqDto;
import com.SE.ITHub.dto.ServicePlanResponseDto;
import com.SE.ITHub.service.ServicePlansServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ServicePlansControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServicePlansServiceImpl servicePlansService;

    @InjectMocks
    private ServicePlansController servicePlansController;

    private ObjectMapper objectMapper;
    private UUID planId;
    private UUID serviceId;
    private ServicePlanReqDto createRequest;
    private ServicePlanResponseDto responseDto;
    private PlansUpdateReqDto updateRequest;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        planId = UUID.randomUUID();
        serviceId = UUID.randomUUID();

        mockMvc = MockMvcBuilders.standaloneSetup(servicePlansController).build();

        createRequest = new ServicePlanReqDto(
                serviceId,
                "Basic Plan",
                new BigDecimal("29.99"),
                "monthly",
                "USD",
                "month",
                "Basic plan description",
                "Feature1,Feature2",
                false,
                1,
                true
        );

        responseDto = ServicePlanResponseDto.builder()
                .id(planId)
                .serviceType("Web Development")
                .planName("Basic Plan")
                .price(new BigDecimal("29.99"))
                .priceType("monthly")
                .description("Basic plan description")
                .features("Feature1,Feature2")
                .isFeatured(false)
                .isActive(true)
                .build();

        updateRequest = new PlansUpdateReqDto();
        updateRequest.setPlanId(planId);
        updateRequest.setServiceId(serviceId);
        updateRequest.setPlanName("Premium Plan");
        updateRequest.setPrice(new BigDecimal("99.99"));
        updateRequest.setPriceType("monthly");
        updateRequest.setCurrency("USD");
        updateRequest.setBillingPeriod("month");
        updateRequest.setDescription("Premium plan");
        updateRequest.setFeatures("Premium features");
        updateRequest.setIsFeatured(true);
        updateRequest.setSortOrder(2);
        updateRequest.setIsActive(true);
    }

    @Test
    void getAllPlans_ShouldReturnList() throws Exception {
        List<ServicePlanResponseDto> plans = Arrays.asList(responseDto);
        when(servicePlansService.getAllPlans()).thenReturn(plans);

        mockMvc.perform(get("/api/service/plans/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(planId.toString()))
                .andExpect(jsonPath("$[0].planName").value("Basic Plan"))
                .andExpect(jsonPath("$[0].price").value(29.99));
    }

    @Test
    void getServicePlansById_ShouldReturnPlans() throws Exception {
        List<ServicePlanResponseDto> plans = Arrays.asList(responseDto);
        when(servicePlansService.getServicePlanByServiceId(serviceId)).thenReturn(plans);

        mockMvc.perform(get("/api/service/plans/serviceId/{id}", serviceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(planId.toString()))
                .andExpect(jsonPath("$[0].planName").value("Basic Plan"));
    }

    @Test
    void createPlan_ShouldReturnOk() throws Exception {
        when(servicePlansService.createServicePlan(any(ServicePlanReqDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/service/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(planId.toString()))
                .andExpect(jsonPath("$.planName").value("Basic Plan"))
                .andExpect(jsonPath("$.price").value(29.99));
    }

    @Test
    void updatePlan_ShouldReturnOk() throws Exception {
        when(servicePlansService.updatePlan(any(PlansUpdateReqDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/api/service/plans/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void deletePlan_ShouldReturnOk() throws Exception {
        doNothing().when(servicePlansService).deleteServicePlan(planId);

        mockMvc.perform(delete("/api/service/plans/{id}", planId))
                .andExpect(status().isOk());
    }
}