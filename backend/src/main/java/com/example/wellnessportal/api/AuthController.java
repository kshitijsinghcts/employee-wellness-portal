package com.example.wellnessportal.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/login")
    public String login() {
        // TODO: Implement login logic
        return "Login endpoint";
    }

    @PostMapping("/register")
    public String register() {
        // TODO: Implement register logic
        return "Register endpoint";
    }

    @PostMapping("/refresh-token")
    public String refreshToken() {
        // TODO: Implement refresh token logic
        return "Refresh token endpoint";
    }
}
