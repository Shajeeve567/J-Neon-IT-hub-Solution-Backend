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

    @PostMapping("/add")
    public ResponseEntity<ServiceResponseDto> createService(@RequestBody ServiceRequestDto reqDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(servicesService.createService(reqDto));
    }


}
