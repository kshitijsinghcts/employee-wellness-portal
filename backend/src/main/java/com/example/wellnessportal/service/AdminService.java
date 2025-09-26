package com.example.wellnessportal.service;

import com.example.wellnessportal.model.AdminDTO;
import com.example.wellnessportal.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for handling administrator-specific operations.
 * This service is used by the `AdminController` to retrieve admin data.
 */
@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Retrieves a list of all administrators and maps them to AdminDTOs.
     * 
     * @return A list of {@link AdminDTO} objects.
     */
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(admin -> new AdminDTO(admin.getEmployeeId(), admin.getName(), admin.getEmail()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single administrator by their ID and maps it to an AdminDTO.
     * 
     * @param id The ID of the administrator to find.
     * @return An {@link Optional} containing the {@link AdminDTO} if found,
     *         otherwise empty.
     */
    public Optional<AdminDTO> getAdminById(Long id) {
        return adminRepository.findById(id)
                .map(admin -> new AdminDTO(admin.getEmployeeId(), admin.getName(), admin.getEmail()));
    }
}
