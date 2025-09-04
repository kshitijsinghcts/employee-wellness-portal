package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
