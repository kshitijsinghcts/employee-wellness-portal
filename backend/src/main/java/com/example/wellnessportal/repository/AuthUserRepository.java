package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    // Users can search for auth users to contact by their employee id or email id. This acts as a filter
    AuthUser findUserByEmployeeId(Long employeeId);

    AuthUser findUserByEmail(String email);

    

}
