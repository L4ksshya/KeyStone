package com.key_stone.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.key_stone.dto.ServiceRequestRequestDTO;
import com.key_stone.dto.ServiceRequestResponseDTO;
import com.key_stone.service.ServiceRequestService;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PatchMapping;

import org.springframework.web.bind.annotation.RequestParam;
import com.key_stone.Entity.ServiceRequestStatus;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    public ServiceRequestController(
            ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @PostMapping
    public ResponseEntity<ServiceRequestResponseDTO> createServiceRequest(
            @Valid @RequestBody ServiceRequestRequestDTO request) {

        ServiceRequestResponseDTO createdRequest =
                serviceRequestService.createServiceRequest(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdRequest);
    }

    @GetMapping
    public List<ServiceRequestResponseDTO> getAllServiceRequests() {

        return serviceRequestService.getAllServiceRequests();
    }
    @GetMapping("/{id}")
    public ServiceRequestResponseDTO getServiceRequestById(
            @PathVariable Long id) {

        return serviceRequestService.getServiceRequestById(id);
    }
    @PatchMapping("/{id}/status")
    public ServiceRequestResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestParam ServiceRequestStatus status) {

        return serviceRequestService.updateStatus(id, status);
    }
    @PutMapping("/{id}")
    public ServiceRequestResponseDTO updateServiceRequest(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRequestRequestDTO request) {

        return serviceRequestService.updateServiceRequest(id, request);
    }
}