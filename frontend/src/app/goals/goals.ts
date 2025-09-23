import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Assuming the Goal interface is defined in a shared types file
export interface Goal {
  id: string;
  title: string;
  description: string;
  type: 'steps' | 'water' | 'sleep' | 'exercise' | 'weight' | 'custom';
  targetValue: number;
  currentValue: number;
  unit: string;
  deadline: string;
  status: 'active' | 'completed' | 'paused' | 'review';
  progress: number;
}

@Component({
  selector: 'app-goals',
  standalone: true,
  imports: [CommonModule, FormsModule, DatePipe],
  templateUrl: './goals.html',
  styleUrl: './goals.css'
})
export class Goals implements OnInit {
  goals: Goal[] = [];
  isDialogOpen = false;

  // Form model for the new goal
  newGoal: Omit<Goal, 'id' | 'currentValue' | 'status' | 'progress'> = {
    title: '',
    description: '',
    type: 'steps',
    targetValue: 0,
    unit: '',
    deadline: '',
  };

  goalTypes: Goal['type'][] = ['steps', 'water', 'sleep', 'exercise', 'weight', 'custom'];

  ngOnInit() {
    this.goals = this.getMockGoals();
  }

  get activeGoals(): Goal[] {
    return this.goals.filter(goal => goal.status === 'active');
  }

  get completedGoals(): Goal[] {
    return this.goals.filter(goal => goal.status === 'completed');
  }

  get pausedGoals(): Goal[] {
    return this.goals.filter(goal => goal.status === 'paused');
  }

  get submittedForReviewGoals(): Goal[] {
    return this.goals.filter(goal => goal.status === 'review');
  }

  get averageProgress(): number {
    if (this.goals.length === 0) return 0;
    const totalProgress = this.goals.reduce((acc, goal) => acc + goal.progress, 0);
    return Math.round(totalProgress / this.goals.length);
  }

  getGoalIcon(type: Goal['type']): string {
    const icons = {
      steps: '🏃',
      water: '💧',
      sleep: '🌙',
      exercise: '🏋️',
      weight: '⚖️',
      custom: '🎯',
    };
    return icons[type] || '🎯';
  }

  openDialog() {
    this.isDialogOpen = true;
  }

  closeDialog() {
    this.isDialogOpen = false;
    this.resetNewGoal();
  }

  handleCreateGoal() {
    if (!this.newGoal.title || !this.newGoal.targetValue || !this.newGoal.deadline) {
      // Basic validation
      return;
    }

    const goal: Goal = {
      id: Date.now().toString(),
      ...this.newGoal,
      currentValue: 0,
      status: 'active',
      progress: 0,
    };

    this.goals.push(goal);
    this.closeDialog();
  }

  resetNewGoal() {
    this.newGoal = {
      title: '',
      description: '',
      type: 'steps',
      targetValue: 0,
      unit: '',
      deadline: '',
    };
  }

  toggleGoalStatus(goalId: string) {
    this.goals = this.goals.map(goal => 
      goal.id === goalId 
        ? { ...goal, status: goal.status === 'active' ? 'paused' : 'active' }
        : goal
    );
  }

  completeGoal(goalId: string) {
    this.goals = this.goals.map(goal => 
      goal.id === goalId 
        ? { ...goal, status: 'completed', progress: 100, currentValue: goal.targetValue }
        : goal
    );
  }

  private getMockGoals(): Goal[] {
    return [
      { id: '1', title: 'Daily Step Goal', description: 'Walk 10,000 steps every day to improve cardiovascular health', type: 'steps', targetValue: 10000, currentValue: 8500, unit: 'steps', deadline: '2024-12-31', status: 'active', progress: 85 },
      { id: '2', title: 'Hydration Challenge', description: 'Drink 8 glasses of water daily for better health', type: 'water', targetValue: 8, currentValue: 8, unit: 'glasses', deadline: '2024-12-31', status: 'completed', progress: 100 },
      { id: '3', title: 'Sleep Optimization', description: 'Get 8 hours of quality sleep each night', type: 'sleep', targetValue: 8, currentValue: 7.2, unit: 'hours', deadline: '2024-12-31', status: 'active', progress: 90 },
      { id: '4', title: 'Weekly Exercise', description: 'Complete 150 minutes of exercise per week', type: 'exercise', targetValue: 150, currentValue: 120, unit: 'minutes', deadline: '2024-12-31', status: 'active', progress: 80 },
      { id: '5', title: 'Weight Loss Goal', description: 'Lose 10 pounds over the next 3 months', type: 'weight', targetValue: 10, currentValue: 6, unit: 'lbs', deadline: '2024-12-31', status: 'active', progress: 60 },
      { id: '6', title: 'Mindfulness Meditation', description: 'Practice 10 minutes of mindfulness daily', type: 'custom', targetValue: 10, currentValue: 10, unit: 'minutes', deadline: '2024-12-31', status: 'review', progress: 100 },
    ];
  }

}
