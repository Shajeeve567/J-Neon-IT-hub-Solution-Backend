package com.SE.ITHub.controller;

import com.SE.ITHub.dto.ServiceRequestDto;
import com.SE.ITHub.dto.ServiceResponseDto;
import com.SE.ITHub.dto.ServiceUpdateDto;
import com.SE.ITHub.service.ServicesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServicesController {

    private final ServicesServiceImpl servicesService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable UUID id){
        servicesService.deleteService(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
