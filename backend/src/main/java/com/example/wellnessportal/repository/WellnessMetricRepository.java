package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.WellnessMetric;
import com.example.wellnessportal.model.Rewards;
import java.util.List;


public interface WellnessMetricRepository extends JpaRepository<WellnessMetric, Long> {
public Rewards findByRewardId(Rewards rewards);
public List<WellnessMetric> findByEmployeeId(Long employeeId);
public int rankByRewards(int scores, Long employeeId);
public int getDailyStepsByEmployeeId(Long employeeId);
public int getWaterIntakeByEmployeeId(Long employeeId);
public List<Rewards> findRewardsByEmployeeId(Long employeeId);
public String findLatestMoodByEmployeeId(Long employeeId);
public int findLatestSleepHoursByEmployeeId(Long employeeId);








}
