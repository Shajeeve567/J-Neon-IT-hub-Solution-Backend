package com.SE.ITHub.service;

import com.SE.ITHub.dto.PlansUpdateReqDto;
import com.SE.ITHub.dto.ServicePlanReqDto;
import com.SE.ITHub.dto.ServicePlanResponseDto;
import com.SE.ITHub.exception.ServiceNotFoundException;
import com.SE.ITHub.mapper.ServicePlanMapper;
import com.SE.ITHub.mapper.ServicesMapper;
import com.SE.ITHub.model.ServicePlans;
import com.SE.ITHub.model.Services;
import com.SE.ITHub.repository.ServicePlansRepository;
import com.SE.ITHub.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicePlansServiceImplTest {

    @Mock
    private ServicePlansRepository servicePlansRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ServicePlanMapper servicePlanMapper;

    @Mock
    private ServicesMapper servicesMapper;

    @InjectMocks
    private ServicePlansServiceImpl servicePlansService;

    private UUID planId;
    private UUID serviceId;
    private ServicePlanReqDto createRequest;
    private ServicePlans servicePlan;
    private ServicePlanResponseDto responseDto;
    private PlansUpdateReqDto updateRequest;
    private Services service;

    @BeforeEach
    void setUp() {
        planId = UUID.randomUUID();
        serviceId = UUID.randomUUID();

        service = new Services();
        service.setId(serviceId);
        service.setTitle("Web Development");

        createRequest = new ServicePlanReqDto(
                serviceId,
                "Basic Plan",
                new BigDecimal("29.99"),
                "monthly",
                "USD",
                "month",
                "Basic plan",
                "Feature1,Feature2",
                false,
                1,
                true
        );

        servicePlan = ServicePlans.builder()
                .id(planId)
                .service(service)
                .planName("Basic Plan")
                .price(new BigDecimal("29.99"))
                .priceType("monthly")
                .currency("USD")
                .billingPeriod("month")
                .description("Basic plan")
                .features("Feature1,Feature2")
                .isFeatured(false)
                .sortOrder(1)
                .isActive(true)
                .build();

        responseDto = ServicePlanResponseDto.builder()
                .id(planId)
                .serviceType("Web Development")
                .planName("Basic Plan")
                .price(new BigDecimal("29.99"))
                .priceType("monthly")
                .description("Basic plan")
                .features("Feature1,Feature2")
                .isFeatured(false)
                .isActive(true)
                .build();

        updateRequest = new PlansUpdateReqDto();
        updateRequest.setPlanId(planId);
        updateRequest.setServiceId(serviceId);
        updateRequest.setPlanName("Updated Plan");
        updateRequest.setPrice(new BigDecimal("49.99"));
        updateRequest.setPriceType("monthly");
        updateRequest.setCurrency("USD");
        updateRequest.setBillingPeriod("month");
        updateRequest.setDescription("Updated plan");
        updateRequest.setFeatures("Updated features");
        updateRequest.setIsFeatured(true);
        updateRequest.setSortOrder(2);
        updateRequest.setIsActive(true);
    }

    @Test
    void createServicePlan_ShouldCreateAndReturnResponse() {
        when(servicePlanMapper.toEntity(createRequest)).thenReturn(servicePlan);
        when(servicePlansRepository.save(servicePlan)).thenReturn(servicePlan);
        when(servicePlanMapper.toResponse(servicePlan)).thenReturn(responseDto);

        ServicePlanResponseDto result = servicePlansService.createServicePlan(createRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(planId);
        assertThat(result.getPlanName()).isEqualTo("Basic Plan");

        verify(servicePlanMapper).toEntity(createRequest);
        verify(servicePlansRepository).save(servicePlan);
        verify(servicePlanMapper).toResponse(servicePlan);
    }

    @Test
    void deleteServicePlan_ShouldDelete() {
        doNothing().when(servicePlansRepository).deleteById(planId);

        servicePlansService.deleteServicePlan(planId);

        verify(servicePlansRepository).deleteById(planId);
    }

    @Test
    void getAllPlans_ShouldReturnAllPlans() {
        List<ServicePlans> plans = Arrays.asList(servicePlan);
        when(servicePlansRepository.findAll()).thenReturn(plans);
        when(servicePlanMapper.toResponse(servicePlan)).thenReturn(responseDto);

        List<ServicePlanResponseDto> result = servicePlansService.getAllPlans();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(planId);

        verify(servicePlansRepository).findAll();
        verify(servicePlanMapper).toResponse(servicePlan);
    }

    @Test
    void updatePlan_ShouldUpdateAndReturnResponse() {
        when(servicePlanMapper.updateEntity(updateRequest)).thenReturn(servicePlan);
        when(servicePlansRepository.save(servicePlan)).thenReturn(servicePlan);
        when(servicePlanMapper.toResponse(servicePlan)).thenReturn(responseDto);

        ServicePlanResponseDto result = servicePlansService.updatePlan(updateRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(planId);

        verify(servicePlanMapper).updateEntity(updateRequest);
        verify(servicePlansRepository).save(servicePlan);
        verify(servicePlanMapper).toResponse(servicePlan);
    }

    @Test
    void getServicePlanByServiceId_WhenServiceNotFound_ShouldThrowException() {
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> servicePlansService.getServicePlanByServiceId(serviceId))
                .isInstanceOf(ServiceNotFoundException.class);

        verify(serviceRepository).findById(serviceId);
        verify(servicePlanMapper, never()).toResponse(any());
    }
}