package com.example.wellnessportal.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.service.AuthService;
import com.example.wellnessportal.model.AuthUser;
import com.example.wellnessportal.model.Employee;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // accepts: {"employeeId":1234, "email":"something" "password":"pass123"}
    @PostMapping("/login")
    public String login(@RequestBody AuthUser authUser) {
        System.err.println(authUser);
        return authService.validateEmployee(authUser);
    }

    // accepts employee details in JSON format: password, employeeId, role, name,
    // email - employee, admin- @portaladmin.com
    // role can be either ADMIN or EMPLOYEE

    // accepts: {"employeeId":1234, "password":"pass123", "name":"John Doe",
    // "email":"something"}
    @PostMapping("/register")
    public String register(@RequestBody Employee employee) {
        if (employee.getEmail() != null && employee.getEmail().endsWith("@portaladmin.com")) {
            employee.setRole("ADMIN");
        } else {
            employee.setRole("EMPLOYEE");
        }
        return authService.registerEmployee(employee);
    }

    @PostMapping("/refresh-token")
    public String refreshToken() {
        // TODO: Implement refresh token logic
        return "Refresh token endpoint";
    }
}
