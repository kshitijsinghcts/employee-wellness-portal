package com.example.wellnessportal.api;

import com.example.wellnessportal.model.AdminDTO;
import com.example.wellnessportal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for administrative operations.
 * Provides endpoints for managing admin users.
 */
@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" })
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * Retrieves a list of all administrators.
     * 
     * @return A ResponseEntity containing a list of AdminDTOs and HTTP status 200
     *         (OK).
     */
    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    /**
     * Retrieves a single administrator by their ID.
     * 
     * @param id The ID of the administrator to retrieve.
     * @return A ResponseEntity containing the AdminDTO if found (status 200 OK),
     *         or a 404 Not Found response if the ID does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
