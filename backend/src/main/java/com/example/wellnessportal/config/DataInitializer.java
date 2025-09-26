package com.example.wellnessportal.config;

import com.example.wellnessportal.model.Admin;
import com.example.wellnessportal.model.AuthUser;
import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.model.Goal;
import com.example.wellnessportal.model.WellnessMetric;
import com.example.wellnessportal.repository.AdminRepository;
import com.example.wellnessportal.repository.AuthUserRepository;
import com.example.wellnessportal.repository.EmployeeRepository;
import com.example.wellnessportal.repository.GoalRepository;
import com.example.wellnessportal.service.RewardsService;
import com.example.wellnessportal.repository.WellnessMetricRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataInitializer implements CommandLineRunner {

        private final AuthUserRepository authUserRepository;
        private final EmployeeRepository employeeRepository;
        private final AdminRepository adminRepository;
        private final WellnessMetricRepository wellnessMetricRepository;
        private final GoalRepository goalRepository;
        private final RewardsService rewardsService;

        public DataInitializer(AuthUserRepository authUserRepository,
                        EmployeeRepository employeeRepository,
                        AdminRepository adminRepository,
                        WellnessMetricRepository wellnessMetricRepository,
                        GoalRepository goalRepository,
                        RewardsService rewardsService) {
                this.authUserRepository = authUserRepository;
                this.employeeRepository = employeeRepository;
                this.adminRepository = adminRepository;
                this.wellnessMetricRepository = wellnessMetricRepository;
                this.goalRepository = goalRepository;
                this.rewardsService = rewardsService;
        }

        @Override
        public void run(String... args) throws Exception {
                // Check if data already exists to prevent re-seeding
                if (authUserRepository.count() > 0) {
                        System.out.println("Database already seeded. Skipping data initialization.");
                        return;
                }

                System.out.println("Seeding database with initial data...");

                // 1. Create 1 Admin
                String adminPassword = "admin_password";
                Admin admin = new Admin(1L, "Admin User", "admin@company.com", adminPassword);
                adminRepository.save(admin);
                authUserRepository.save(new AuthUser(1L, "admin@company.com", adminPassword, "ADMIN"));

                // 2. Create 10 Employees
                List<Employee> employees = new ArrayList<>();
                String[] names = {
                                "Liam Smith", "Olivia Johnson", "Noah Williams", "Emma Brown", "Oliver Jones",
                                "Ava Garcia", "Elijah Miller", "Charlotte Davis", "William Rodriguez", "Sophia Martinez"
                };

                for (int i = 0; i < names.length; i++) {
                        long id = i + 2L; // Start employee IDs from 2
                        String name = names[i];
                        String email = name.toLowerCase().replace(" ", ".") + "@company.com";
                        String password = "password" + (i + 1);

                        Employee employee = new Employee(id, password, name, email);
                        employees.add(employee);
                        authUserRepository.save(new AuthUser(id, email, password, "EMPLOYEE"));
                }
                employeeRepository.saveAll(employees);

                // 3. Create 10 days of wellness data for each employee
                String[] moods = { "Happy", "Neutral", "Sad" };
                List<WellnessMetric> metrics = new ArrayList<>();
                for (Employee employee : employees) {
                        for (int day = 0; day < 10; day++) {
                                metrics.add(new WellnessMetric(employee.getEmployeeId(), LocalDate.now().minusDays(day),
                                                moods[ThreadLocalRandom.current().nextInt(moods.length)],
                                                ThreadLocalRandom.current().nextInt(4, 9),
                                                ThreadLocalRandom.current().nextInt(2000, 15001),
                                                ThreadLocalRandom.current().nextInt(2, 11)));
                        }
                }
                wellnessMetricRepository.saveAll(metrics);

                // 4. Create goals for each employee
                List<Goal> goals = new ArrayList<>();
                int[] possiblePoints = { 10, 20, 50, 100, 200 };
                for (Employee employee : employees) {
                        // Active goals (-1)
                        goals.add(new Goal(employee.getEmployeeId(), "Walk 10,000 steps daily",
                                        "Maintain a consistent daily step count for better cardiovascular health.",
                                        LocalDate.now().plusMonths(1), "10000 steps"));
                        goals.add(new Goal(employee.getEmployeeId(), "Drink 8 glasses of water",
                                        "Stay hydrated throughout the day to improve energy levels.",
                                        LocalDate.now().plusWeeks(2), "8 glasses"));

                        // In Review goals (0)
                        Goal reviewGoal = new Goal(employee.getEmployeeId(), "Achieve 7 hours of sleep",
                                        "Ensure at least 7 hours of quality sleep per night for a week.",
                                        LocalDate.now().minusDays(1), "7 hours/night");
                        reviewGoal.setStatus(0);
                        goals.add(reviewGoal);

                        Goal reviewGoal2 = new Goal(employee.getEmployeeId(), "Eat 5 servings of vegetables",
                                        "Incorporate five servings of vegetables into daily meals.",
                                        LocalDate.now().minusDays(2), "5 servings");
                        reviewGoal2.setStatus(0);
                        goals.add(reviewGoal2);

                        // Completed goals (1)
                        Goal completedGoal = new Goal(employee.getEmployeeId(), "Complete a 30-minute workout",
                                        "Engage in a 30-minute workout session three times this week.",
                                        LocalDate.now().minusWeeks(1), "3 sessions");
                        completedGoal.setStatus(1);
                        completedGoal.setPoints(
                                        possiblePoints[ThreadLocalRandom.current().nextInt(possiblePoints.length)]);
                        goals.add(completedGoal);

                        Goal completedGoal2 = new Goal(employee.getEmployeeId(), "Read for 20 minutes before bed",
                                        "Read a book for 20 minutes each night to unwind and improve sleep quality.",
                                        LocalDate.now().minusWeeks(2), "20 minutes/day");
                        completedGoal2.setStatus(1);
                        completedGoal2.setPoints(
                                        possiblePoints[ThreadLocalRandom.current().nextInt(possiblePoints.length)]);
                        goals.add(completedGoal2);
                }
                goalRepository.saveAll(goals);

                // 5. Grant achievements for the seeded wellness data
                for (WellnessMetric metric : metrics) {
                        rewardsService.checkAndGrantRewards(metric);
                }

                System.out.println("Database seeding complete: 1 admin, 10 employees, " + metrics.size()
                                + " metric records, and " + goals.size() + " goals created.");
                System.out.println("Initial achievements granted for seeded data.");
        }
}