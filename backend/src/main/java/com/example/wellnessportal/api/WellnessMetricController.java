// package com.example.wellnessportal.api;

// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/wellness")
// public class WellnessController {
//     @PostMapping
//     public String submitMetrics() {
//         // TODO: Implement submit metrics logic
//         return "Submit wellness metrics endpoint";
//     }

//     @GetMapping
//     public String viewMetrics() {
//         // TODO: Implement view metrics logic
//         return "View wellness metrics endpoint";
//     }
// }

package com.example.wellnessportal.api;

import com.example.wellnessportal.model.WellnessMetric;
import com.example.wellnessportal.service.WellnessMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/wellness")
public class WellnessMetricController {

    @Autowired
    private WellnessMetricService wellnessMetricService;

    // POST: Log a wellness metric using request body
    @PostMapping("/log")
    public WellnessMetric logMetric(@RequestBody WellnessMetric wellnessMetric) {
        return wellnessMetricService.saveWellnessMetric(
                wellnessMetric.getEmployeeId(),
                LocalDate.now(),
                wellnessMetric.getMood(),
                wellnessMetric.getSleepHours(),
                wellnessMetric.getDailySteps(),
                wellnessMetric.getWaterIntake()
        );
    }

    // GET: Get all logs for employeeId = 1 (hardcoded for testing)
    @GetMapping("/logs")
    public List<WellnessMetric> getLogs() {
        return wellnessMetricService.getEmployeeLogs(1L);
    }

    // GET: Get overall status for employeeId = 1
    @GetMapping("/status")
    public String getStatus() {
        return wellnessMetricService.getOverallWellnessMetricsStatus(1L);
    }

    // GET: Get latest metrics for employeeId = 1
    @GetMapping("/latest")
    public List<String> getLatestMetrics() {
        return wellnessMetricService.getLatestWellnessMetrics(1L);
    }

    // GET: Get rank for employeeId = 1
    @GetMapping("/rank")
    public int getRank() {
        return wellnessMetricService.getEmployeeRank(1L);
    }
}

