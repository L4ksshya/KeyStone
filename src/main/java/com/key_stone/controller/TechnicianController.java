package com.key_stone.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.key_stone.dto.TechnicianRequestDTO;
import com.key_stone.dto.TechnicianResponseDTO;
import com.key_stone.service.TechnicianService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/technicians")
public class TechnicianController {

    private final TechnicianService technicianService;

    public TechnicianController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @PostMapping
    public ResponseEntity<TechnicianResponseDTO> createTechnician(
            @Valid @RequestBody TechnicianRequestDTO request) {

        TechnicianResponseDTO createdTechnician =
                technicianService.createTechnician(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTechnician);
    }

    @GetMapping
    public List<TechnicianResponseDTO> getAllTechnicians() {

        return technicianService.getAllTechnicians();
    }
    @PutMapping("/{id}")
    public TechnicianResponseDTO updateTechnician(
            @PathVariable Long id,
            @Valid @RequestBody TechnicianRequestDTO request) {

        return technicianService.updateTechnician(id, request);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnician(@PathVariable Long id) {

        technicianService.deleteTechnician(id);

        return ResponseEntity.noContent().build();
    }
}