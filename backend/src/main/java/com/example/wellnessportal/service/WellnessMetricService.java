package com.example.wellnessportal.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.model.AuthUser;
import com.example.wellnessportal.model.Goal;
import com.example.wellnessportal.model.Rewards;
import com.example.wellnessportal.model.WellnessMetric;
import com.example.wellnessportal.repository.AuthUserRepository;
import com.example.wellnessportal.repository.WellnessMetricRepository;

@Service
public class WellnessMetricService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private GoalService goalService;

    @Autowired
    private WellnessMetricRepository wellnessMetricRepository;

    // User logs his health metrics. He should do this periodically for best results
    // Daily steps in goal entity is named activityLevel here due to contextual
    // differences.
    public WellnessMetric saveWellnessMetric(Long employeeId,
            LocalDate date,
            String mood,
            int sleepHours,
            int dailySteps,
            int waterIntake) {

        AuthUser authUser = authUserRepository.findById(employeeId).orElse(null);

        if (authUser == null) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        // Determine rewards based on input values
        /*
         * Rewards is of the form: {mood reward,
         * sleepHours reward,
         * activityLevel reward,
         * waterIntake reward
         * }
         */

        List<Rewards> rewards = calculateRewards(mood, sleepHours, dailySteps, waterIntake);

        WellnessMetric wellnessMetric = new WellnessMetric(
                employeeId,
                date,
                mood,
                sleepHours,
                dailySteps,
                waterIntake,
                rewards);

        return wellnessMetricRepository.save(wellnessMetric);
    }

    public WellnessMetric saveWellnessMetric(WellnessMetric wellnessMetric) {
        if (wellnessMetric == null || wellnessMetric.getEmployeeId() == null) {
            throw new IllegalArgumentException("WellnessMetric and Employee ID cannot be null.");
        }

        // Validate employee exists
        authUserRepository.findById(wellnessMetric.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));

        // Ensure record date is set
        if (wellnessMetric.getrecordDate() == null) {
            wellnessMetric.setrecordDate(LocalDate.now());
        }

        // Calculate and set rewards
        List<Rewards> rewards = calculateRewards(
                wellnessMetric.getMood(),
                wellnessMetric.getSleepHours(),
                wellnessMetric.getDailySteps(),
                wellnessMetric.getWaterIntake());
        wellnessMetric.setRewards(rewards);

        return wellnessMetricRepository.save(wellnessMetric);
    }

    // List of metrics logged by the employee since his/her account creation
    public List<WellnessMetric> getEmployeeLogs(Long employeeId) 
    {
        List<WellnessMetric> wm=wellnessMetricRepository.findAllByEmployeeId(employeeId);
        return wm;
    }

    // The following methods are for letting the user know how he/she is on par with
    // his self-set goals
    // They can also be used for analytics on UI:
    
    //Getting overall wellness metric status for all metrics of the user
    //Useful for dashboard and analytics
    public String getOverallWellnessMetricsStatus(Long employeeId)
    {
      
        WellnessMetric wellnessMetric=wellnessMetricRepository.findByEmployeeId(employeeId);
        if(wellnessMetric==null)
        return "Employee does not exist";
        if(goalService.validateGoalCompletion(employeeId,
                                              wellnessMetric,
                                              LocalDate.now()))
        return "On Track";
        else
            return "Behind Schedule";
    }

    //Getting overall wellness metric status individually for each goal
    //Response to User Request
    public String getOverallWellnessMetricsStatus(Long employeeId, 
                                                  Goal goal)
    {
       
        WellnessMetric wellnessMetric=wellnessMetricRepository.findByEmployeeId(employeeId);
          if(wellnessMetric==null)
        return "Employee does not exist";
        if(goalService.validateGoalCompletion(employeeId,
                                              wellnessMetric,
                                              goal,
                                              LocalDate.now()))
        return "On Track";
        else
            return "Behind Schedule";
    }

    //To display on employee or admin analytics dashboard
    public List<String> getLatestWellnessMetrics(Long employeeId)
    {
       // This method from repository interface returns the latest row and is limited to one row
       WellnessMetric wellnessMetric=wellnessMetricRepository.findByEmployeeId(employeeId);
         if(wellnessMetric==null)
        return List.of("Employee does not exist");
       List<String> wmList=new ArrayList<>();
       wmList.add(wellnessMetric.getMood());
       wmList.add(String.valueOf(wellnessMetric.getDailySteps()));
       wmList.add(String.valueOf(wellnessMetric.getSleepHours()));
       wmList.add(String.valueOf(wellnessMetric.getWaterIntake()));
       
       return wmList;
    }

    // We can use this method to rank the employees based on how they fare among
    // health metrics. We can configure different weight vectors for this.
    // This can be used for admin to track his/her employee health statistics
    // The code run time will be optimized in further editions
    public int getEmployeeRank(Long employeeId) {
        // The employees are ranked by their metrics in this list
        // The formula used is weighted sum of metrics
        // The weights can be set by the user, allowing him to prioritize his/her
        // metrics
        List<Long> rankedList = wellnessMetricRepository.findEmployeesRankedByHealthScore();

        for (int i = 0; i < rankedList.size(); i++) {
            if (rankedList.get(i).equals(employeeId)) {
                return i + 1;
            }
        }

        return -1;
    }

    private List<Rewards> calculateRewards(String mood, int sleepHours, int dailySteps, int waterIntake) {
        List<Rewards> rewards = new ArrayList<>();

        // Mood-based reward
        if (mood != null) {
            if (mood.equalsIgnoreCase("Happy")) {
                rewards.add(Rewards.PLATINUM);
            } else if (mood.equalsIgnoreCase("Neutral")) {
                rewards.add(Rewards.GOLD);
            } else {
                rewards.add(Rewards.SILVER);
            }
        }

        // Sleep-based reward
        if (sleepHours >= 8) {
            rewards.add(Rewards.PLATINUM);
        } else if (sleepHours >= 6) {
            rewards.add(Rewards.GOLD);
        } else if (sleepHours >= 5) {
            rewards.add(Rewards.SILVER);
        } else if (sleepHours >= 4) {
            rewards.add(Rewards.BRONZE);
        }

        // Activity-based reward
        if (dailySteps >= 9000) {
            rewards.add(Rewards.PLATINUM);
        } else if (dailySteps >= 7000) {
            rewards.add(Rewards.GOLD);
        } else if (dailySteps >= 5000) {
            rewards.add(Rewards.SILVER);
        } else if (dailySteps >= 3000) {
            rewards.add(Rewards.BRONZE);
        }

        // Water intake-based reward
        if (waterIntake >= 8) {
            rewards.add(Rewards.PLATINUM);
        } else if (waterIntake >= 6) {
            rewards.add(Rewards.GOLD);
        } else if (waterIntake >= 4) {
            rewards.add(Rewards.SILVER);
        } else if (waterIntake >= 2) {
            rewards.add(Rewards.BRONZE);
        }

        return rewards;
    }

}
