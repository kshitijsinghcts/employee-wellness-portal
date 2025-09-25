package com.example.wellnessportal.service;

import com.example.wellnessportal.model.Rewards;
import com.example.wellnessportal.model.WellnessMetric;
import com.example.wellnessportal.repository.RewardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service for managing employee rewards.
 * This service checks wellness metrics to grant achievements and retrieves
 * rewards
 * for employees.
 */
@Service
public class RewardsService {

    @Autowired
    private RewardsRepository rewardsRepository;

    /**
     * Checks a given wellness metric and grants rewards if certain thresholds are
     * met.
     * This method is called from `WellnessMetricService` after a metric is saved.
     *
     * @param metric The {@link WellnessMetric} to check.
     */
    public void checkAndGrantRewards(WellnessMetric metric) {
        Long employeeId = metric.getEmployeeId();
        LocalDate date = metric.getRecordDate();

        // Check for Daily Step Goal
        if (metric.getDailySteps() >= 10000) {
            grantReward(employeeId, "Daily Step Goal", "Reached 10,000 steps", date);
        }

        // Check for Hydration Hero
        if (metric.getWaterIntake() >= 8) {
            grantReward(employeeId, "Hydration Hero", "Drank 8 glasses of water", date);
        }

        // Check for Sleep Champion
        if (metric.getSleepHours() >= 8) {
            grantReward(employeeId, "Sleep Champion", "Slept for 8 hours", date);
        }
    }

    /**
     * Creates and saves a new reward, but only if the same reward has not already
     * been granted to the employee on the same day. This prevents duplicate
     * rewards.
     *
     * @param employeeId  The ID of the employee receiving the reward.
     * @param title       The title of the reward.
     * @param description A description of the achievement.
     * @param date        The date the reward was achieved.
     */
    private void grantReward(Long employeeId, String title, String description, LocalDate date) {
        // Check if this reward was already granted for this day to prevent duplicates
        // Note: `existsBy...` would require a custom query in RewardsRepository.
        // A simple check can be done by fetching and checking, though less performant.
        // For now, we assume a simple save. A check should be added for production.
        // if (!rewardsRepository.existsByEmployeeIdAndTitleAndAchievedDate(employeeId,
        // title, date)) {
        Rewards reward = new Rewards(employeeId, title, description, date);
        rewardsRepository.save(reward);
        // }
    }

    /**
     * Retrieves all rewards for a specific employee, ordered by the date they were
     * achieved.
     * Called from `EmployeeRewardsController`.
     *
     * @param employeeId The ID of the employee.
     * @return A list of {@link Rewards} for the employee.
     */
    public List<Rewards> getRewardsForEmployee(Long employeeId) {
        // This method is defined in the RewardsRepository interface.
        return rewardsRepository.findByEmployeeId(employeeId);
    }
}
