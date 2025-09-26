package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.AuthUser;

/**
 * Repository interface for AuthUser entity.
 * Provides access to AuthUser data using Spring Data JPA.
 * Used by the AuthUserService class to abstract database operations.
 */
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    // Users can search for auth users to contact by their employee id or email id.
    // This acts as a filter

    // The following method is commented out but can be used to fetch AuthUser by employee ID.
    // Uncomment and use in service/controller if needed.
    // @Query("SELECT u FROM AuthUser u WHERE u.employeeId = :employeeId")
    // AuthUser findUserByEmployeeId(@Param("employeeId") Long employeeId);

    /**
     * Finds an AuthUser by their email address.
     * 
     * @param email the email address of the user
     * @return AuthUser entity matching the given email
     * 
  
     */
    @Query("SELECT u FROM AuthUser u WHERE u.email = :email")
    AuthUser findUserByEmail(@Param("email") String email);

}
