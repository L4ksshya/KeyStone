package com.key_stone.dto;

import com.key_stone.Entity.ServiceRequestPriority;
import com.key_stone.Entity.ServiceRequestStatus;

public class ServiceRequestResponseDTO {

    private Long id;
    private String title;
    private String description;
    private ServiceRequestStatus status;
    private ServiceRequestPriority priority;
    private Long customerId;

    public ServiceRequestResponseDTO() {
    }

    public ServiceRequestResponseDTO(
            Long id,
            String title,
            String description,
            ServiceRequestStatus status,
            ServiceRequestPriority priority,
            Long customerId) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceRequestStatus status) {
        this.status = status;
    }

    public ServiceRequestPriority getPriority() {
        return priority;
    }

    public void setPriority(ServiceRequestPriority priority) {
        this.priority = priority;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}