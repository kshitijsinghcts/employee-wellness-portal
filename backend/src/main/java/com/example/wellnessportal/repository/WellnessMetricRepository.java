package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wellnessportal.model.WellnessMetric;
import com.example.wellnessportal.model.Rewards;
import java.util.List;


public interface WellnessMetricRepository extends JpaRepository<WellnessMetric, Long> {

    @Query("SELECT r FROM Rewards r WHERE r.rewardId = :#{#rewards.rewardId}")
    Rewards findByRewardId(@Param("rewards") Rewards rewards);

   

    @Query("SELECT wm FROM WellnessMetric wm WHERE wm.employeeId = :employeeId ORDER BY wm.date DESC LIMIT 1")
    WellnessMetric findByEmployeeId(@Param("employeeId") Long employeeId);

       @Query("SELECT wm FROM WellnessMetric wm WHERE wm.employeeId = :employeeId")
        List<WellnessMetric> findAllByEmployeeId(@Param("employeeId") Long employeeId);

        @Query("SELECT employeeId FROM WellnessMetric wm ORDER BY (e.waterIntake * 1.0 + e.dailySteps * 0.01 + e.sleepHours * 1.5) DESC")
        List<Long> findEmployeesRankedByHealthScore();


    //Will be implemented based on required analytics

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
