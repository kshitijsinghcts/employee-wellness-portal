package com.example.wellnessportal.service;

import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.model.Rewards;
import com.example.wellnessportal.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setEmail(updatedEmployee.getEmail());
                    employee.setRole(updatedEmployee.getRole());
                    employee.setScores(updatedEmployee.getScores());
                    employee.setRewards(updatedEmployee.getRewards());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
    public void updateRewards(Rewards rewards, 
                              Long employeeId)
    {
    Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

    if (optionalEmployee.isPresent()) {
        Employee employee = optionalEmployee.get();
        List<Rewards> currentRewards = employee.getRewards();

        currentRewards.add(rewards); // Add new reward to the list
        employee.setRewards(currentRewards); // Update the list

        employeeRepository.save(employee); // Persist changes
    } 
    }

}
  

