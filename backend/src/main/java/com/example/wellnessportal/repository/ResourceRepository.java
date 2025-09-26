package com.example.wellnessportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> 
{

    // Search by partial title match (contains) for more efficient search feature. 
    // Since exact match is also included in this, another method for exact match is redundant
    @Query("SELECT r FROM Resource r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Resource> findResourcesByTitleContaining(@Param("title") String title);

    // Search by ID (can be replaced by findById from JpaRepository)
    // Added for developer convenience
    @Query("SELECT r FROM Resource r WHERE r.id = :id")
    Resource findResourceById(@Param("id") Long id);

    // Search by category
    @Query("SELECT r FROM Resource r WHERE LOWER(r.resourceCategory) = LOWER(:resourceCategory)")
    List<Resource> findResourceByResourceCategory(@Param("resourceCategory") String resourceCategory);

    // Search by tag 
    @Query("SELECT r FROM Resource r JOIN r.resourceTags t WHERE LOWER(t) IN :tags")
    List<Resource> findResourcesByTag(@Param("tags") List<String> tags);

}
