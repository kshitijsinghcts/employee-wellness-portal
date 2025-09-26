package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.Employee;

/**
 * Repository interface for Employee entity.
 * Provides access to Employee data using Spring Data JPA.
 * Used by the EmployeeService class to abstract database operations.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Finds an Employee by their employee ID.
     * 
     * @param employeeId the employee ID of the employee
     * @return Employee entity matching the given employee ID
     * 
     * Usage in Service:
     *     employeeRepository.findEmployeeByEmployeeId(id);
     * 
     */
    @Query("SELECT e FROM Employee e WHERE e.employeeId = :employeeId")
    Employee findEmployeeByEmployeeId(@Param("employeeId") Long employeeId);

    /**
     * Finds an Employee by their email address.
     * 
     * @param email the email address of the employee
     * @return Employee entity matching the given email
     * 
     * Usage in Service:
     *     employeeRepository.findEmployeeByEmail(email);
     * 
     */
    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Employee findEmployeeByEmail(@Param("email") String email);

}