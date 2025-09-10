package com.example.wellnessportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint for employee login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam Long employeeId,
                                        @RequestParam String password) 
    {
        String result = authService.validateEmployee(employeeId, password);
        return ResponseEntity.ok(result);
    }

    // Endpoint for employee registration
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Employee employee) 
    {
        String result = authService.registerEmployee(employee);
        return ResponseEntity.ok(result);
    }
}
