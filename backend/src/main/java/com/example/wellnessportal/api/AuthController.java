package com.example.wellnessportal.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.service.AuthService;
import com.example.wellnessportal.model.Admin;
import com.example.wellnessportal.model.AuthUser;
import com.example.wellnessportal.model.Employee;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // accepts {email, password}
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthUser authUser) {
        String token = authService.validateEmployee(authUser);

        if (token != null && token.startsWith("dummy-token")) {
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            // The frontend expects the token directly as a string, not in a JSON object.
            // The login.ts file parses this string: `response.split('-')`
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(token);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Employee employee) {
        String result = authService.registerEmployee(employee);
        if (result.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        // Handles both "already exists" and "ID is required"
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin) {
        String result = authService.registerAdmin(admin);
        if (result.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @PostMapping("/refresh-token")
    public String refreshToken() {
        // TODO: Implement refresh token logic
        return "Refresh token endpoint";
    }
}
