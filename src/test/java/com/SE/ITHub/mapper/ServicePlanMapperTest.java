package com.SE.ITHub.mapper;

import com.SE.ITHub.dto.ServicePlanReqDto;
import com.SE.ITHub.dto.ServicePlanResponseDto;
import com.SE.ITHub.dto.PlansUpdateReqDto;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicePlanMapperTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ServicePlansRepository servicePlansRepository;

    @InjectMocks
    private ServicePlanMapper mapper;

    private UUID serviceId;
    private Services service;

    @BeforeEach
    void setUp() {
        serviceId = UUID.randomUUID();

        service = new Services();
        service.setId(serviceId);
        service.setTitle("Hosting");
    }

    @Test
    void toResponse_ShouldMapCorrectly() {
        ServicePlans plan = new ServicePlans();
        plan.setId(UUID.randomUUID());
        plan.setPlanName("Pro");
        plan.setPrice(new BigDecimal("20"));
        plan.setService(service);

        ServicePlanResponseDto response = mapper.toResponse(plan);

        assertThat(response).isNotNull();
        assertThat(response.getPlanName()).isEqualTo("Pro");
        assertThat(response.getPrice()).isEqualTo(new BigDecimal("20"));
        assertThat(response.getServiceType()).isEqualTo("Hosting");
    }

}