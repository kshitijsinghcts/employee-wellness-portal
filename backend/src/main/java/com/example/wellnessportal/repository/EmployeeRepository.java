package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.model.Rewards;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByEmployeeId(Long employeeId);
    Employee findEmployeeByEmail(String employeeId);
    List<Rewards> findRewardsByEmployeeId(Long employeeId);
    int findScoresByEmployeeId(Long employeeId);

}
