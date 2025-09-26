package com.example.wellnessportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.Resource;

/**
 * Repository interface for Resource entity.
 * Provides access to resource-related data using Spring Data JPA.
 * 
 * <p><b>Service Layer:</b> Typically used in ResourceService to handle business logic.</p>
 */
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    // Search by partial title match (contains) for more efficient search feature. 
    // Since exact match is also included in this, another method for exact match is redundant

    /**
     * Finds resources whose titles contain the given keyword (case-insensitive).
     * 
     * @param title partial or full title to search for
     * @return list of Resource entities matching the title pattern
     */
    @Query("SELECT r FROM Resource r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Resource> findResourcesByTitleContaining(@Param("title") String title);

    // Search by ID (can be replaced by findById from JpaRepository)
    // Added for developer convenience

    /**
     * Finds a resource by its unique ID.
     * 
     * @param id the ID of the resource
     * @return the Resource entity with the given ID
     */
    @Query("SELECT r FROM Resource r WHERE r.id = :id")
    Resource findResourceById(@Param("id") Long id);

    // Search by category

    /**
     * Finds resources by their category (case-insensitive).
     * 
     * @param resourceCategory the category to filter by
     * @return list of Resource entities matching the category
     */
    @Query("SELECT r FROM Resource r WHERE LOWER(r.resourceCategory) = LOWER(:resourceCategory)")
    List<Resource> findResourceByResourceCategory(@Param("resourceCategory") String resourceCategory);

    // Search by tag 

    /**
     * Finds resources that contain any of the specified tags (case-insensitive).
     * 
     * @param tags list of tags to match
     * @return list of Resource entities that have at least one matching tag
     */
    @Query("SELECT r FROM Resource r JOIN r.resourceTags t WHERE LOWER(t) IN :tags")
    List<Resource> findResourcesByTag(@Param("tags") List<String> tags);

}