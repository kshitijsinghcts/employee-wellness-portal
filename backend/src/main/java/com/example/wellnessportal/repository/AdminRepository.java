package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    //Users can search for admins to contact by their employee id or email id. This acts as a filter
    @Query("SELECT a FROM Admin a WHERE a.employeeId = :employeeId")
    Admin findAdminByEmployeeId(@Param("employeeId") Long employeeId);

    // @Query("SELECT a FROM Admin a WHERE a.email = :email")
    // Admin findAdminByEmail(@Param("email") String email);


}
