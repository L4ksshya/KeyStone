package com.key_stone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.key_stone.Entity.ServiceRequest;

public interface ServiceRequestRepository
        extends JpaRepository<ServiceRequest, Long> {
}