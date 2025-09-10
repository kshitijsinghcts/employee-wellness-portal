package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    //Users can search for admins to contact by their employee id or email id. This acts as a filter
    Admin findAdminByEmployeeId(Long employeeId);

    Admin findAdminByEmail(String email);

}
