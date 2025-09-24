import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GoalsService } from '../services/goals.service';
import { AuthService } from '../services/auth.service';

export interface Goal {
  // `id` is used in admin-panel, `goalId` is used here. Let's align them.
  // The backend returns `goalId`, so we'll use that and map if needed.
  goalId: number;
  employeeId: number;
  title: string;
  description: string;
  targetDate: string; // YYYY-MM-DD
  targetValue: string; // e.g., "10000 steps"
  status: number; // -1: active, 0: review, 1: completed
  // Frontend-only properties for display
  displayStatus?: 'active' | 'completed' | 'review';
  progress?: number;
  type?: 'steps' | 'water' | 'sleep' | 'exercise' | 'weight' | 'custom';
}

@Component({
  selector: 'app-goals',
  standalone: true,
  imports: [CommonModule, FormsModule, DatePipe],
  templateUrl: './goals.html',
  styleUrl: './goals.css',
  providers: [GoalsService]
})
export class Goals implements OnInit {
  goals: Goal[] = [];
  isDialogOpen = false;
  employeeId: number | null = null;

  // Form model for the new goal
  newGoal: Partial<Goal> = {
    title: '',
    description: '',
    targetValue: '',
    targetDate: '',
  };

  constructor(private goalsService: GoalsService, private authService: AuthService) {}

  ngOnInit() {
    const id = localStorage.getItem('employeeId');
    if (id) {
      this.employeeId = parseInt(id, 10);
    } else {
      console.error('Employee ID not found. User might not be logged in.');
      // Optionally, redirect to login
      // this.authService.logout();
      // this.router.navigate(['']);
    }
    this.loadGoals();
  }

  loadGoals() {
    if (!this.employeeId) return;
    this.goalsService.getGoals(this.employeeId).subscribe(goals => {
      this.goals = goals.map(goal => this.enrichGoalForDisplay(goal));
    }, error => console.error('Failed to load goals:', error));
  }

  private enrichGoalForDisplay(goal: Goal): Goal {
    const enrichedGoal = { ...goal };
    // Map backend status to frontend display status
    switch (goal.status) {
      case 1:
        enrichedGoal.displayStatus = 'completed';
        enrichedGoal.progress = 100;
        break;
      case 0:
        enrichedGoal.displayStatus = 'review';
        enrichedGoal.progress = 100;
        break;
      case -1:
      default:
        enrichedGoal.displayStatus = 'active';
        enrichedGoal.progress = 50; // Placeholder progress
        break;
    }

    // Infer type from title for icon
    const title = goal.title.toLowerCase();
    if (title.includes('step')) enrichedGoal.type = 'steps';
    else if (title.includes('water')) enrichedGoal.type = 'water';
    else if (title.includes('sleep')) enrichedGoal.type = 'sleep';
    else if (title.includes('exercise')) enrichedGoal.type = 'exercise';
    else if (title.includes('weight')) enrichedGoal.type = 'weight';
    else enrichedGoal.type = 'custom';

    return enrichedGoal;
  }

  get activeGoals(): Goal[] {
    return this.goals.filter(goal => goal.displayStatus === 'active');
  }

  get completedGoals(): Goal[] {
    return this.goals.filter(goal => goal.displayStatus === 'completed');
  }

  get submittedForReviewGoals(): Goal[] {
    return this.goals.filter(goal => goal.displayStatus === 'review');
  }

  get averageProgress(): number {
    if (this.goals.length === 0) return 0;
    const totalProgress = this.goals.reduce((acc, goal) => acc + (goal.progress || 0), 0);
    return Math.round(totalProgress / this.goals.length);
  }

  getGoalIcon(type: Goal['type'] | undefined): string {
    const icons = {
      steps: '🏃',
      water: '💧',
      sleep: '🌙',
      exercise: '🏋️',
      weight: '⚖️',
      custom: '🎯',
    };
    return type ? icons[type] : '🎯';
  }

  openDialog() {
    this.isDialogOpen = true;
  }

  closeDialog() {
    this.isDialogOpen = false;
    this.resetNewGoal();
  }

  handleCreateGoal() {
    if (!this.newGoal.title || !this.newGoal.targetValue || !this.newGoal.targetDate) {
      // Basic validation
      return;
    }

    if (!this.employeeId) {
      console.error('Cannot create goal without an employee ID.');
      return;
    }

    const goalToCreate: Partial<Goal> = {
      employeeId: this.employeeId,
      title: this.newGoal.title,
      description: this.newGoal.description,
      targetDate: this.newGoal.targetDate,
      targetValue: this.newGoal.targetValue,
      status: -1, // Explicitly set status to active on creation
    };

    this.goalsService.createGoal(goalToCreate).subscribe(() => {
      this.loadGoals(); // Refresh the list
      this.closeDialog();
    }, error => console.error('Failed to create goal:', error));
  }

  resetNewGoal() {
    this.newGoal = {
      title: '',
      description: '',
      targetValue: '',
      targetDate: '',
    };
  }

  completeGoal(goalToComplete: Goal) {
    if (!this.employeeId) return;

    // Create a clean payload for the update request
    const updatedGoal: Goal = {
      goalId: goalToComplete.goalId,
      employeeId: goalToComplete.employeeId,
      title: goalToComplete.title,
      description: goalToComplete.description,
      targetDate: goalToComplete.targetDate,
      targetValue: goalToComplete.targetValue,
      status: 0, // Set status to 'review'
    };

    this.goalsService.updateGoal(updatedGoal).subscribe(() => {
      this.loadGoals(); // Refresh the list
    }, error => console.error('Failed to complete goal:', error));
  }

  deleteGoal(goalId: number) {
    if (!this.employeeId) return;
    if (confirm('Are you sure you want to delete this goal?')) {
      this.goalsService.deleteGoal(goalId).subscribe(() => {
        this.loadGoals(); // Refresh the list
      }, error => console.error('Failed to delete goal:', error));
    }
  }

}
