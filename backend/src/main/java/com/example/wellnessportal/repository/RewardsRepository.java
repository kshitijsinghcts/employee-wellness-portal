package com.example.wellnessportal.repository;

import com.example.wellnessportal.model.Rewards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RewardsRepository extends JpaRepository<Rewards, Long> {
    List<Rewards> findByEmployeeIdOrderByAchievedDateAsc(Long employeeId);

    boolean existsByEmployeeIdAndTitleAndAchievedDate(Long employeeId, String title, LocalDate achievedDate);
}
