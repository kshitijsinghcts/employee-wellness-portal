import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Employee } from '../models/employee.model';
import { Goal } from '../models/goal.model';
import { FormsModule } from '@angular/forms';
import { Rewards } from '../models/rewards.enum';

interface MetricDisplay {
  label: string;
  value: string | number;
  icon: string;
  unit: string;
}

@Component({
  selector: 'app-admin-panel',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-panel.html',
  styleUrls: ['./admin-panel.css']
})
export class AdminPanelComponent implements OnInit {
  employees: Employee[] = [];
  selectedEmployee: Employee | null = null;
  latestMetrics: MetricDisplay[] = [];
  completedGoals: Goal[] = [];
  filteredEmployees: Employee[] = [];
  searchTerm: string = '';

  // Dummy data for demonstration
  private dummyEmployees: Employee[] = [
    { employeeId: 101, name: 'Alice Johnson', email: 'alice@example.com' },
    { employeeId: 102, name: 'Bob Williams', email: 'bob@example.com' },
    { employeeId: 103, name: 'Charlie Brown', email: 'charlie@example.com' },
    { employeeId: 104, name: 'Diana Prince', email: 'diana@example.com' },
    { employeeId: 105, name: 'Ethan Hunt', email: 'ethan@example.com' },
  ];

  private dummyMetrics: { [key: number]: MetricDisplay[] } = {
    101: [
      { label: 'Steps', value: 8500, icon: '👟', unit: 'steps' },
      { label: 'Water', value: 7, icon: '💧', unit: 'glasses' },
      { label: 'Sleep', value: 8, icon: '🛌', unit: 'hours' },
      { label: 'Mood', value: 'Happy', icon: '😊', unit: '' }
    ],
    102: [
      { label: 'Steps', value: 12000, icon: '👟', unit: 'steps' },
      { label: 'Water', value: 8, icon: '💧', unit: 'glasses' },
      { label: 'Sleep', value: 6, icon: '🛌', unit: 'hours' },
      { label: 'Mood', value: 'Energetic', icon: '⚡', unit: '' }
    ],
    103: [
      { label: 'Steps', value: 4500, icon: '👟', unit: 'steps' },
      { label: 'Water', value: 5, icon: '💧', unit: 'glasses' },
      { label: 'Sleep', value: 7, icon: '🛌', unit: 'hours' },
      { label: 'Mood', value: 'Calm', icon: '😌', unit: '' }
    ],
    // Add more dummy data as needed
  };

  private dummyGoals: { [key: number]: Goal[] } = {
    101: [
      { goalId: 1, employeeId: 101, goalType: 'steps', targetDate: '2023-10-31', status: 1, targetScores: 10000, targetRewards: Rewards.BRONZE, description: 'Walk 10k steps daily' }
    ],
    102: [
      { goalId: 2, employeeId: 102, goalType: 'water', targetDate: '2023-10-31', status: 1, targetScores: 8, targetRewards: Rewards.BRONZE, description: 'Drink 8 glasses of water' },
      { goalId: 3, employeeId: 102, goalType: 'sleep', targetDate: '2023-10-31', status: 1, targetScores: 7, targetRewards: Rewards.BRONZE, description: 'Achieve 7 hours of sleep' }
    ],
    104: [
      { goalId: 4, employeeId: 104, goalType: 'steps', targetDate: '2023-12-31', status: 1, targetScores: 1000000, targetRewards: Rewards.SILVER, description: 'Reach 1 million steps this year' }
    ]
  };

  constructor() {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.employees = this.dummyEmployees.sort((a, b) => a.name.localeCompare(b.name));
    this.filteredEmployees = this.employees;
  }

  selectEmployee(employee: Employee): void {
    // If the same employee is clicked, close the dropdown. Otherwise, open the new one.
    if (this.selectedEmployee?.employeeId === employee.employeeId) {
      this.selectedEmployee = null;
      return;
    }

    this.selectedEmployee = employee;
    
    // Fetch dummy wellness metrics
    this.latestMetrics = this.dummyMetrics[employee.employeeId] || [];

    // Fetch dummy completed goals
    const allGoals = this.dummyGoals[employee.employeeId] || [];
    this.completedGoals = allGoals.filter(goal => goal.status >= goal.targetScores);
  }

  getGoalProgress(goal: Goal): number {
    if (goal.targetScores === 0) return 100;
    return Math.min(100, (goal.status / goal.targetScores) * 100);
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

  getGoalIcon(goalType: string): string {
    if (goalType.toLowerCase().includes('step')) return '🏃';
    if (goalType.toLowerCase().includes('water')) return '💧';
    if (goalType.toLowerCase().includes('sleep')) return '😴';
    return '🎯';
  }
}