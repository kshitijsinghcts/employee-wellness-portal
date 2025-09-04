package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

}
