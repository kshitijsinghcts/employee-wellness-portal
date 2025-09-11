package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.Employee;



public interface EmployeeRepository extends JpaRepository<Employee, Long> 
{
    
    @Query("SELECT e FROM Employee e WHERE e.employeeId = :employeeId")
    Employee findEmployeeByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Employee findEmployeeByEmail(@Param("email") String email);

    

  


}
