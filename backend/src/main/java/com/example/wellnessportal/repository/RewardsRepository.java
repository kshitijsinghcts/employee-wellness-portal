package com.example.wellnessportal.repository;

import com.example.wellnessportal.model.Rewards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the {@link Rewards} entity.
 * Provides standard CRUD operations and custom queries for managing employee
 * rewards.
 */
@Repository
public interface RewardsRepository extends JpaRepository<Rewards, Long> {
    /**
     * Finds all rewards earned by a specific employee.
     * 
     * @param employeeId The ID of the employee.
     * @return A list of {@link Rewards} objects.
     */
    List<Rewards> findByEmployeeId(Long employeeId);

    /**
     * Checks if a specific reward has already been granted to an employee on a
     * specific date.
     */
    boolean existsByEmployeeIdAndTitleAndAchievedDate(Long employeeId, String title, LocalDate achievedDate);
}