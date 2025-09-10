package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    // Custom query method example
    Resource findResourceByTitle(String title);
    Resource findResourceById(Long id);
    Resource findResourceByType(String type);
    Resource findResourceByResourceCategory(String category);
    
}
