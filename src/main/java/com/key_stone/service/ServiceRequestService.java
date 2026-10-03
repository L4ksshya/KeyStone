package com.key_stone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.key_stone.Entity.ServiceRequest;
import com.key_stone.Entity.ServiceRequestStatus;
import com.key_stone.dto.ServiceRequestRequestDTO;
import com.key_stone.dto.ServiceRequestResponseDTO;
import com.key_stone.repository.ServiceRequestRepository;
import com.key_stone.exception.ServiceRequestNotFoundException;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestService(
            ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public ServiceRequestResponseDTO createServiceRequest(
            ServiceRequestRequestDTO request) {

        ServiceRequest serviceRequest = new ServiceRequest();

        serviceRequest.setTitle(request.getTitle());
        serviceRequest.setDescription(request.getDescription());
        serviceRequest.setPriority(request.getPriority());
        serviceRequest.setCustomerId(request.getCustomerId());

        // New requests always start as OPEN
        serviceRequest.setStatus(ServiceRequestStatus.OPEN);

        ServiceRequest savedRequest =
                serviceRequestRepository.save(serviceRequest);

        return convertToResponseDTO(savedRequest);
    }

    public List<ServiceRequestResponseDTO> getAllServiceRequests() {

        return serviceRequestRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    private ServiceRequestResponseDTO convertToResponseDTO(
            ServiceRequest serviceRequest) {

        return new ServiceRequestResponseDTO(
                serviceRequest.getId(),
                serviceRequest.getTitle(),
                serviceRequest.getDescription(),
                serviceRequest.getStatus(),
                serviceRequest.getPriority(),
                serviceRequest.getCustomerId()
        );
    }
    public ServiceRequestResponseDTO getServiceRequestById(Long id) {

        ServiceRequest serviceRequest =
                serviceRequestRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceRequestNotFoundException(
                                "Service request not found with id: " + id));

        return convertToResponseDTO(serviceRequest);
    }
    public ServiceRequestResponseDTO updateStatus(
            Long id,
            ServiceRequestStatus status) {

        ServiceRequest serviceRequest =
                serviceRequestRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceRequestNotFoundException(
                                "Service request not found with id: " + id));

        serviceRequest.setStatus(status);

        ServiceRequest updatedRequest =
                serviceRequestRepository.save(serviceRequest);

        return convertToResponseDTO(updatedRequest);
    }
    public ServiceRequestResponseDTO updateServiceRequest(
            Long id,
            ServiceRequestRequestDTO request) {

        ServiceRequest serviceRequest =
                serviceRequestRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceRequestNotFoundException(
                                "Service request not found with id: " + id));

        serviceRequest.setTitle(request.getTitle());
        serviceRequest.setDescription(request.getDescription());
        serviceRequest.setPriority(request.getPriority());
        serviceRequest.setCustomerId(request.getCustomerId());

        ServiceRequest updatedRequest =
                serviceRequestRepository.save(serviceRequest);

        return convertToResponseDTO(updatedRequest);
    }
}