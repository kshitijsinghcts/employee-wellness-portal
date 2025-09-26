package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.WellnessMetric;

import java.util.List;
import java.time.LocalDate;

/**
 * Repository interface for WellnessMetric entity.
 * Provides access to wellness tracking data using Spring Data JPA.
 * 
 * <p><b>Service Layer:</b> Typically used in WellnessMetricService to handle business logic.</p>
 */
public interface WellnessMetricRepository extends JpaRepository<WellnessMetric, Long> {

    // @Query("SELECT r FROM Rewards r WHERE r.rewardId = :#{#rewards.rewardId}")
    // Rewards findByRewardId(@Param("rewards") Rewards rewards);

    /**
     * Retrieves the latest wellness metric record for a specific employee.
     * 
     * <p><b>Note:</b> JPQL does not support LIMIT directly. Consider using Pageable or native query for accurate results.</p>
     * 
     * @param employeeId the ID of the employee
     * @return the most recent WellnessMetric entry for the employee
     */
    @Query("SELECT wm FROM WellnessMetric wm WHERE wm.employeeId = :employeeId ORDER BY wm.recordDate DESC LIMIT 1")
    WellnessMetric findByEmployeeId(@Param("employeeId") Long employeeId);

    /**
     * Retrieves all wellness metric records for a specific employee.
     * 
     * @param employeeId the ID of the employee
     * @return list of WellnessMetric entries for the employee
     */
    @Query("SELECT wm FROM WellnessMetric wm WHERE wm.employeeId = :employeeId")
    List<WellnessMetric> findAllByEmployeeId(@Param("employeeId") Long employeeId);

    /**
     * Ranks employees based on a calculated health score.
     * 
     * <p><b>Formula:</b> waterIntake * 1.0 + dailySteps * 0.01 + sleepHours * 1.5</p>
     * 
     * @return list of employee IDs sorted by descending health score
     */
    @Query("SELECT employeeId FROM WellnessMetric wm ORDER BY (wm.waterIntake * 1.0 + wm.dailySteps * 0.01 + wm.sleepHours * 1.5) DESC")
    List<Long> findEmployeesRankedByHealthScore();

    /**
     * Retrieves a wellness metric record for a specific employee on a specific date.
     * 
     * @param employeeId the ID of the employee
     * @param date the date of the record
     * @return the WellnessMetric entry for the given employee and date
     */
    @Query("SELECT wm FROM WellnessMetric wm WHERE wm.employeeId = :employeeId AND wm.recordDate = :date")
    WellnessMetric findByEmployeeIdAndRecordDate(@Param("employeeId") Long employeeId, @Param("date") LocalDate date);

    // Will be implemented based on required analytics

    // @Query("SELECT wm.dailySteps FROM WellnessMetric wm WHERE wm.employeeId = :employeeId ORDER BY wm.date DESC LIMIT 1")
    // int getDailyStepsByEmployeeId(@Param("employeeId") Long employeeId);

    // @Query("SELECT wm.waterIntake FROM WellnessMetric wm WHERE wm.employeeId = :employeeId ORDER BY wm.date DESC LIMIT 1")
    // int getWaterIntakeByEmployeeId(@Param("employeeId") Long employeeId);

    // @Query("SELECT r FROM Rewards r WHERE r.employeeId = :employeeId")
    // List<Rewards> findRewardsByEmployeeId(@Param("employeeId") Long employeeId);

    // @Query("SELECT wm.mood FROM WellnessMetric wm WHERE wm.employeeId = :employeeId ORDER BY wm.date DESC LIMIT 1")
    // String findLatestMoodByEmployeeId(@Param("employeeId") Long employeeId);

    // @Query("SELECT wm.sleepHours FROM WellnessMetric wm WHERE wm.employeeId = :employeeId ORDER BY wm.date DESC LIMIT 1")
    // int findLatestSleepHoursByEmployeeId(@Param("employeeId") Long employeeId);
}