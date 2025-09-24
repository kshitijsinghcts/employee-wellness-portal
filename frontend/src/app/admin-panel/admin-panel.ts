import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Employee } from '../models/employee.model';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from '../services/admin.service';
import { WellnessService } from '../services/wellness.service';
import { GoalsService } from '../services/goals.service';
import { WellnessMetric } from '../models/wellness-metric.model';
import { Goal } from '../goals/goals';
import { AuthService } from '../services/auth.service';

interface MetricDisplay {
  label: string;
  value: string | number;
  icon: string;
  unit: string;
}

@Component({
  selector: 'app-admin-panel',
  standalone: true,
  imports: [CommonModule, FormsModule], // RouterModule is not needed if we inject Router
  templateUrl: './admin-panel.html',
  styleUrls: ['./admin-panel.css']
})
export class AdminPanelComponent implements OnInit {
  employees: Employee[] = [];
  selectedEmployee: Employee | null = null;
  isLoadingDetails = false;
  latestMetrics: MetricDisplay[] = [];
  reviewGoals: Goal[] = [];
  filteredEmployees: Employee[] = [];
  searchTerm: string = '';

  constructor(
    private adminService: AdminService,
    private wellnessService: WellnessService,
    private goalsService: GoalsService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.adminService.getAllEmployees().subscribe({
      next: (employees) => {
        this.employees = employees.sort((a, b) => a.name.localeCompare(b.name));
        this.filteredEmployees = this.employees;
      },
      error: (err) => console.error('Failed to load employees', err)
    });
  }

  selectEmployee(employee: Employee): void {
    // If the same employee is clicked, close the dropdown. Otherwise, open the new one.
    if (this.selectedEmployee?.employeeId === employee.employeeId) {
      this.selectedEmployee = null;
      return;
    }

    this.selectedEmployee = employee;
    this.isLoadingDetails = true;
    this.latestMetrics = [];
    this.reviewGoals = [];

    // Fetch wellness metrics
    this.wellnessService.getWellnessMetrics(String(employee.employeeId)).subscribe({
      next: (metrics) => {
        const latest = metrics.sort((a, b) => new Date(b.recordDate).getTime() - new Date(a.recordDate).getTime())[0];
        if (latest) {
          this.latestMetrics = this.transformMetricsForDisplay(latest);
        }
        this.isLoadingDetails = false;
      },
      error: (err) => {
        console.error(`Failed to load metrics for employee ${employee.employeeId}`, err);
        this.isLoadingDetails = false;
      }
    });

    // Fetch goals
    this.goalsService.getGoals(employee.employeeId).subscribe({
      next: (goals) => {
        // Filter for goals submitted for review (status 0)
        this.reviewGoals = goals.filter(goal => goal.status === 0);
      },
      error: (err) => console.error(`Failed to load goals for employee ${employee.employeeId}`, err)
    });
  }

  private transformMetricsForDisplay(metric: WellnessMetric): MetricDisplay[] {
    return [
      { label: 'Steps', value: metric.dailySteps, icon: '👟', unit: 'steps' },
      { label: 'Water', value: metric.waterIntake, icon: '💧', unit: 'glasses' },
      { label: 'Sleep', value: metric.sleepHours, icon: '🛌', unit: 'hours' },
      { label: 'Mood', value: metric.mood, icon: '😊', unit: '' }
    ];
  }

  filterEmployees(): void {
    if (!this.searchTerm) {
      this.filteredEmployees = this.employees;
    } else {
      const lowerCaseSearchTerm = this.searchTerm.toLowerCase();
      this.filteredEmployees = this.employees.filter(employee => employee.name.toLowerCase().includes(lowerCaseSearchTerm));
    }
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.filterEmployees();
  }

  getGoalIcon(title: string): string {
    if (title.toLowerCase().includes('step')) return '🏃';
    if (title.toLowerCase().includes('water')) return '💧';
    if (title.toLowerCase().includes('sleep')) return '😴';
    return '🎯';
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['']); // Navigate to the login page
  }
}