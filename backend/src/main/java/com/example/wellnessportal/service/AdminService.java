package com.example.wellnessportal.service;

import com.example.wellnessportal.model.Admin;
import com.example.wellnessportal.model.AdminDTO;
import com.example.wellnessportal.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(admin -> new AdminDTO(admin.getEmployeeId(), admin.getName(), admin.getEmail()))
                .collect(Collectors.toList());
    }

    public Optional<AdminDTO> getAdminById(Long id) {
        return adminRepository.findById(id)
                .map(admin -> new AdminDTO(admin.getEmployeeId(), admin.getName(), admin.getEmail()));
    }
}
