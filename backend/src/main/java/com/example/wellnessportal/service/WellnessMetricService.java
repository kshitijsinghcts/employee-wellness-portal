package com.example.wellnessportal.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.model.WellnessMetric;
import com.example.wellnessportal.repository.AuthUserRepository;
import com.example.wellnessportal.repository.WellnessMetricRepository;

/**
 * Service for managing employee wellness metrics.
 * Handles saving, retrieving, and analyzing wellness data, including checking
 * for rewards.
 */
@Service
public class WellnessMetricService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private RewardsService rewardsService;

    @Autowired
    private WellnessMetricRepository wellnessMetricRepository;

    /**
     * Saves or updates a wellness metric for an employee.
     * If a metric for the given employee and date already exists, it's updated.
     * Otherwise, a new metric is created. After saving, it checks for and grants
     * any earned rewards.
     *
     * @param wellnessMetric The {@link WellnessMetric} object to save.
     * @return The saved or updated WellnessMetric.
     * @throws IllegalArgumentException if the metric or employee ID is null, or if
     *                                  the employee does not exist.
     */
    public WellnessMetric saveWellnessMetric(WellnessMetric wellnessMetric) {
        if (wellnessMetric == null || wellnessMetric.getEmployeeId() == null) {
            throw new IllegalArgumentException("WellnessMetric and Employee ID cannot be null.");
        }

        // Validate employee exists
        authUserRepository.findById(wellnessMetric.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));

        // Ensure record date is set
        if (wellnessMetric.getRecordDate() == null) {
            wellnessMetric.setRecordDate(LocalDate.now());
        }

        // Check for existing record for this employee and date
        WellnessMetric existing = wellnessMetricRepository.findByEmployeeIdAndRecordDate(
                wellnessMetric.getEmployeeId(), wellnessMetric.getRecordDate());

        if (existing != null) {
            // Update existing record
            existing.setMood(wellnessMetric.getMood());
            existing.setSleepHours(wellnessMetric.getSleepHours());
            existing.setDailySteps(wellnessMetric.getDailySteps());
            existing.setWaterIntake(wellnessMetric.getWaterIntake());
            WellnessMetric savedMetric = wellnessMetricRepository.save(existing);
            rewardsService.checkAndGrantRewards(savedMetric);
            return savedMetric;
        } else {
            WellnessMetric savedMetric = wellnessMetricRepository.save(wellnessMetric);
            rewardsService.checkAndGrantRewards(savedMetric);
            return savedMetric;
        }
    }

    /**
     * Retrieves all wellness metric logs for a specific employee.
     *
     * @param employeeId The ID of the employee.
     * @return A list of all {@link WellnessMetric} objects for the employee.
     */
    public List<WellnessMetric> getEmployeeLogs(Long employeeId) {
        return wellnessMetricRepository.findAllByEmployeeId(employeeId);
    }

    /**
     * Calculates an employee's rank based on a health score derived from their
     * recent wellness metrics.
     * The ranking is determined by a weighted sum of metrics from the last 10
     * days, as calculated in the `findEmployeesRankedByHealthScore` native query.
     *
     * @param employeeId The ID of the employee to rank.
     * @return The rank of the employee (1-based index), or -1 if the employee is
     *         not found in the ranked list.
     */
    public int getEmployeeRank(Long employeeId) {
        List<Long> rankedList = wellnessMetricRepository.findEmployeesRankedByHealthScore();

        for (int i = 0; i < rankedList.size(); i++) {
            if (rankedList.get(i).equals(employeeId)) {
                return i + 1;
            }
        }

        return -1;
    }

}
