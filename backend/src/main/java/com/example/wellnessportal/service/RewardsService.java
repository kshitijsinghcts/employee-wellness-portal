package com.example.wellnessportal.service;

import com.example.wellnessportal.model.Rewards;
import com.example.wellnessportal.model.WellnessMetric;
import com.example.wellnessportal.repository.RewardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RewardsService {

    @Autowired
    private RewardsRepository rewardsRepository;

    public void checkAndGrantRewards(WellnessMetric metric) {
        Long employeeId = metric.getEmployeeId();
        LocalDate date = metric.getRecordDate();

        // Check for Daily Step Goal
        if (metric.getDailySteps() >= 10000) {
            grantReward(employeeId, "Daily Step Goal", "Reached 10000 steps", date);
        }

        // Check for Hydration Hero
        if (metric.getWaterIntake() >= 8) {
            grantReward(employeeId, "Hydration Hero", "Had 8 glasses of water", date);
        }

        // Check for Sleep Champion
        if (metric.getSleepHours() >= 8) {
            grantReward(employeeId, "Sleep Champion", "Completed 8 hours of sleep", date);
        }
    }

    private void grantReward(Long employeeId, String title, String description, LocalDate date) {
        // Check if this reward was already granted for this day to prevent duplicates
        if (!rewardsRepository.existsByEmployeeIdAndTitleAndAchievedDate(employeeId, title, date)) {
            Rewards reward = new Rewards(employeeId, title, description, date);
            rewardsRepository.save(reward);
        }
    }

    public List<Rewards> getRewardsForEmployee(Long employeeId) {
        return rewardsRepository.findByEmployeeIdOrderByAchievedDateAsc(employeeId);
    }
}
