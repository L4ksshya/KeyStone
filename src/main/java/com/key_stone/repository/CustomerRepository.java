package com.key_stone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.key_stone.Entity.Customer;

public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
}