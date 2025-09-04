package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
