package com.key_stone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.key_stone.Entity.UserAuthentication;

public interface UserAuthenticationRepository
        extends JpaRepository<UserAuthentication, Long> {

    Optional<UserAuthentication> findByUsername(String username);
}