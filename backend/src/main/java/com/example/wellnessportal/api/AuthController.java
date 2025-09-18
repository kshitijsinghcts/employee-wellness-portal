package com.example.wellnessportal.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.service.AuthService;
import com.example.wellnessportal.model.Admin;
import com.example.wellnessportal.model.AuthUser;
import com.example.wellnessportal.model.Employee;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody AuthUser authUser) {
        return authService.validateEmployee(authUser);
    }

    @PostMapping("/register")
    public String register(@RequestBody Employee employee) {
        return authService.registerEmployee(employee);
    }

    @PostMapping("/register-admin")
    public String registerAdmin(@RequestBody Admin admin) {
        return authService.registerAdmin(admin);
    }

    @PostMapping("/refresh-token")
    public String refreshToken() {
        // TODO: Implement refresh token logic
        return "Refresh token endpoint";
    }
}
