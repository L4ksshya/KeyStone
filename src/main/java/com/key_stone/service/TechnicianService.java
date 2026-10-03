package com.key_stone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.key_stone.Entity.Technician;
import com.key_stone.dto.TechnicianRequestDTO;
import com.key_stone.dto.TechnicianResponseDTO;
import com.key_stone.exception.CustomerEmailAlreadyExistsException;
import com.key_stone.exception.CustomerNotFoundException;
import com.key_stone.repository.TechnicianRepository;

@Service
public class TechnicianService {

    private final TechnicianRepository technicianRepository;

    public TechnicianService(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    public TechnicianResponseDTO createTechnician(TechnicianRequestDTO request) {

        if (technicianRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomerEmailAlreadyExistsException(
                    "Technician with email already exists: " + request.getEmail());
        }

        Technician technician = new Technician();

        technician.setName(request.getName());
        technician.setEmail(request.getEmail());
        technician.setPhone(request.getPhone());
        technician.setSpecialization(request.getSpecialization());

        Technician savedTechnician = technicianRepository.save(technician);

        return new TechnicianResponseDTO(
                savedTechnician.getId(),
                savedTechnician.getName(),
                savedTechnician.getEmail(),
                savedTechnician.getPhone(),
                savedTechnician.getSpecialization()
        );
    }

    public List<TechnicianResponseDTO> getAllTechnicians() {

        return technicianRepository.findAll()
                .stream()
                .map(technician -> new TechnicianResponseDTO(
                        technician.getId(),
                        technician.getName(),
                        technician.getEmail(),
                        technician.getPhone(),
                        technician.getSpecialization()
                ))
                .toList();
    }
    public TechnicianResponseDTO updateTechnician(
            Long id,
            TechnicianRequestDTO request) {

        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() ->
                    new CustomerNotFoundException(
                        "Technician not found with id: " + id));

        technician.setName(request.getName());
        technician.setEmail(request.getEmail());
        technician.setPhone(request.getPhone());
        technician.setSpecialization(request.getSpecialization());

        Technician updatedTechnician =
                technicianRepository.save(technician);

        return new TechnicianResponseDTO(
                updatedTechnician.getId(),
                updatedTechnician.getName(),
                updatedTechnician.getEmail(),
                updatedTechnician.getPhone(),
                updatedTechnician.getSpecialization()
        );
    }
    public void deleteTechnician(Long id) {

        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() ->
                    new CustomerNotFoundException(
                        "Technician not found with id: " + id));

        technicianRepository.delete(technician);
    }
}