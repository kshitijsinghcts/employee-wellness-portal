package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    // Users can search for auth users to contact by their employee id or email id.
    // This acts as a filter
    // @Query("SELECT u FROM AuthUser u WHERE u.employeeId = :employeeId")
    // AuthUser findUserByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT u FROM AuthUser u WHERE u.email = :email")
    AuthUser findUserByEmail(@Param("email") String email);

}
