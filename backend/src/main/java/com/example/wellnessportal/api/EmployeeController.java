package com.example.wellnessportal.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @GetMapping
    public String getEmployeeInfo() {
        // TODO: Implement get employee info logic
        return "Get employee info endpoint";
    }

    @PutMapping
    public String updateEmployeeInfo() {
        // TODO: Implement update employee info logic
        return "Update employee info endpoint";
    }
}
