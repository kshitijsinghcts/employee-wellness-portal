package com.example.wellnessportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.model.Admin;
import com.example.wellnessportal.model.Employee;
import com.example.wellnessportal.model.AuthUser;
import com.example.wellnessportal.model.Goal;
import com.example.wellnessportal.model.WellnessMetric;
import com.example.wellnessportal.repository.AdminRepository;
import com.example.wellnessportal.repository.EmployeeRepository;
import com.example.wellnessportal.repository.AuthUserRepository;
import com.example.wellnessportal.repository.GoalRepository;
import com.example.wellnessportal.repository.WellnessMetricRepository;

/**
 * Service for handling authentication and user registration logic.
 * This service is used by the `AuthController`.
 */
@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private WellnessMetricRepository wellnessMetricRepository;

    /**
     * Validates user credentials against the database.
     *
     * @param inputAuthUser An {@link AuthUser} object containing the email and
     *                      password from the login request.
     * @return A dummy token string containing the user's role and ID on success,
     *         or an error message string on failure.
     */
    public String validateEmployee(AuthUser inputAuthUser) {

        String email = inputAuthUser.getEmail();

        AuthUser authUser = authUserRepository.findUserByEmail(email);

        if (authUser == null) {
            return "User not found.";
        }
        if (!inputAuthUser.getPassword().equals(authUser.getPassword())) {
            return "Incorrect password.";
        }
        Long employeeId = authUser.getEmployeeId();

        String role = authUser.getRole();

        if ("ADMIN".equals(role)) {
            Admin admin = adminRepository.findAdminByEmployeeId(employeeId);
            return (admin != null) ? "dummy-token-for-admin-" + employeeId : null;
        } else if ("EMPLOYEE".equals(role)) {
            Employee employee = employeeRepository.findEmployeeByEmployeeId(employeeId);
            return (employee != null) ? "dummy-token-for-employee-" + employeeId : null;
        }
        return "Invalid role.";
    }

    /**
     * Registers a new employee in the system.
     * It saves the user details in both the `Employee` and `AuthUser` tables.
     *
     * @param employee An {@link Employee} object containing the details of the new
     *                 user.
     * @return A success message if registration is successful, or an error
     *         message if the ID is missing or already exists.
     */
    public String registerEmployee(Employee employee) {
        if (employee.getEmployeeId() == null) {
            return "Employee ID is required.";
        }
        if (employeeRepository.existsById(employee.getEmployeeId())
                ||
                authUserRepository.existsById(employee.getEmployeeId())) {
            return "Employee with ID " + employee.getEmployeeId() + " already exists. Kindly login.";
        }

        employeeRepository.save(employee);
        authUserRepository.save(new AuthUser(
                employee.getEmployeeId(),
                employee.getEmail(),
                employee.getPassword(),
                "EMPLOYEE"));

        return "Employee registered successfully with ID " + employee.getEmployeeId();
    }

    /**
     * Registers a new administrator in the system.
     * It saves the user details in both the `Admin` and `AuthUser` tables, and
     * initializes their `Goal` and `WellnessMetric` records.
     *
     * @param admin An {@link Admin} object containing the details of the new
     *              admin.
     * @return A success message if registration is successful, or an error
     *         message if the ID is missing or already exists.
     */
    public String registerAdmin(Admin admin) {
        if (admin.getEmployeeId() == null) {
            return "Admin Employee ID is required.";
        }
        if (employeeRepository.existsById(admin.getEmployeeId())
                ||
                authUserRepository.existsById(admin.getEmployeeId())) {
            return "Admin with ID " + admin.getEmployeeId() + " already exists. Kindly login.";
        }

        adminRepository.save(admin);
        authUserRepository.save(new AuthUser(
                admin.getEmployeeId(),
                admin.getEmail(),
                admin.getPassword(),
                "ADMIN"));

        // Initialize related records for the new admin.
        Goal goalRecord = new Goal(admin.getEmployeeId());
        WellnessMetric wellnessMetricRecord = new WellnessMetric(admin.getEmployeeId());

        goalRepository.save(goalRecord);
        wellnessMetricRepository.save(wellnessMetricRecord);

        return "Admin registered successfully with ID " + admin.getEmployeeId();
    }
}
