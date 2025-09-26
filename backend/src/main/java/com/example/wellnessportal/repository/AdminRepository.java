package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.Admin;

/**
 * Repository interface for Admin entity.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 * Used by the AdminService class to interact with the database.
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Users can search for admins to contact by their employee id or email id.
    // This acts as a filter.

    /**
     * Finds an admin by their employee ID.
     * 
     * @param employeeId the employee ID of the admin
     * @return Admin entity matching the given employee ID
     * 
     
     */
    @Query("SELECT a FROM Admin a WHERE a.employeeId = :employeeId")
    Admin findAdminByEmployeeId(@Param("employeeId") Long employeeId);

    /**
     * Finds an admin by their email address.
     * 
     * @param email the email address of the admin
     * @return Admin entity matching the given email
     * 

     */
    @Query("SELECT a FROM Admin a WHERE a.email = :email")
    Admin findAdminByEmail(@Param("email") String email);

}