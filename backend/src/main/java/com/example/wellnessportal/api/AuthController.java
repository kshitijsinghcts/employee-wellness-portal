package com.example.wellnessportal.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.wellnessportal.service.AuthService;
import com.example.wellnessportal.model.Admin;
import com.example.wellnessportal.model.AuthUser;
import com.example.wellnessportal.model.Employee;

/**
 * REST controller for authentication-related operations like login and
 * registration.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Authenticates a user and returns a dummy token upon successful
     * validation.
     * 
     * @param authUser A JSON object in the request body containing the user's email
     *                 and password.
     *                 Example: `{ "email": "user@example.com", "password":
     *                 "password123" }`
     * @return A ResponseEntity containing the token string on success (status 200
     *         OK),
     *         or an error message with a 401 Unauthorized status if credentials are
     *         invalid.
     *         The frontend expects the token directly as a string.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthUser authUser) {
        String token = authService.validateEmployee(authUser);

        if (token != null && token.startsWith("dummy-token")) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    /**
     * Registers a new employee.
     * 
     * @param employee A JSON object in the request body with employee details.
     *                 Example: `{ "employeeId": 123, "name": "John Doe", "email":
     *                 "john.doe@example.com", "password": "password123" }`
     * @return A ResponseEntity with a success message and status 201 (Created),
     *         or an error message with status 400 (Bad Request) if registration
     *         fails (e.g., user already exists).
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Employee employee) {
        String result = authService.registerEmployee(employee);
        if (result.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        // Handles both "already exists" and "ID is required"
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    /**
     * Registers a new administrator.
     * 
     * @param admin A JSON object in the request body with admin details.
     *              Example: `{ "employeeId": 1, "name": "Admin User", "email":
     *              "admin@example.com", "password": "adminpass" }`
     * @return A ResponseEntity with a success message and status 201 (Created),
     *         or an error message with status 400 (Bad Request) if registration
     *         fails.
     */
    @PostMapping("/register-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin) {
        String result = authService.registerAdmin(admin);
        if (result.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    /**
     * Placeholder for a refresh token endpoint.
     * 
     * @return A string indicating the endpoint's purpose.
     */
    @PostMapping("/refresh-token")
    public String refreshToken() {
        // TODO: Implement refresh token logic
        return "Refresh token endpoint";
    }
}
