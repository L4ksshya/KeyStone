package com.key_stone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.key_stone.Entity.Technician;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    Optional<Technician> findByEmail(String email);
}