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

    // POST: Log a wellness metric
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

    // POST: Get all logs for an employee
    @PostMapping("/logs")
    public List<WellnessMetric> getLogs(@RequestBody WellnessMetric wellnessMetric) {
        return wellnessMetricService.getEmployeeLogs(wellnessMetric.getEmployeeId());
    }

    // POST: Get overall status
    @PostMapping("/status")
    public String getStatus(@RequestBody WellnessMetric wellnessMetric) {
        return wellnessMetricService.getOverallWellnessMetricsStatus(wellnessMetric.getEmployeeId());
    }

    // POST: Get latest metrics
    @PostMapping("/latest")
    public List<String> getLatestMetrics(@RequestBody WellnessMetric wellnessMetric) {
        return wellnessMetricService.getLatestWellnessMetrics(wellnessMetric.getEmployeeId());
    }

    // POST: Get employee rank
    @PostMapping("/rank")
    public int getRank(@RequestBody WellnessMetric wellnessMetric) {
        return wellnessMetricService.getEmployeeRank(wellnessMetric.getEmployeeId());
    }
}
