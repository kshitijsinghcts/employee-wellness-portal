import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeService } from '../services/employee.service';
import { RouterModule } from '@angular/router';
import { Employee } from '../models/employee.model';
import { WellnessService } from '../services/wellness.service';
import { WellnessMetric } from '../models/wellness-metric.model';
import { RewardsService, Reward as ApiReward } from '../services/rewards.service';
import { GoalsService } from '../services/goals.service';

// Mock data and types based on the provided dashboard.tsx
// In a real app, these would be in separate models/services.

interface Metric {
  label: string;
  value: number | string;
  target: number | string;
  unit: string;
  icon: string; // Icon name placeholder
  progress: number;
}

interface DisplayAchievement {
  title: string;
  description: string;
  icon: string;
  date: string;
}

interface DisplayReward {
    title: string;
    cost: number;
    icon: string;
    available: boolean;
}

interface WeeklyChartData {
  dateLabel: string;
  steps: number;
  water: number;
  sleep: number;
}

@Component({
  standalone: true,
  selector: 'app-dashboard',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
  imports: [CommonModule, FormsModule, RouterModule]
})
export class DashboardComponent implements OnInit {
  user: (Employee & { role?: string }) | null = null;
  todaysMetrics: Metric[] = [];
  isEditing = false;
  editMetricData: {
    mood: string;
   sleepHours: number;
    dailySteps: number;
    waterIntake: number;
  } = {
    mood: 'Neutral',
    sleepHours: 0,
    dailySteps: 0,
    waterIntake: 0
  };
  moodOptions = ['Happy', 'Neutral', 'Sad'];

  showToast = false;
  toastMessage = '';

 // Mock data, can be replaced with real data from a service
  userPoints = { current: 250 };

 recentAchievements: DisplayAchievement[] = [];

  pointRewards: DisplayReward[] = [
    { title: 'Coffee Voucher', cost: 500, icon: 'Gift', available: true },
   { title: 'Gym Day Pass', cost: 1000, icon: 'Activity', available: true },
    { title: 'Wellness Book', cost: 800, icon: 'Award', available: true },
    { title: 'Massage Session', cost: 2000, icon: 'Heart', available: false },
  ];

  allMetrics: WellnessMetric[] = [];
  selectedDate = new Date();
  greeting = '';
  weeklyChartData: WeeklyChartData[] = [];
  chartColors = { steps: '#22c55e', water: '#3b82f6', sleep: '#8b5cf6' };
 
  constructor(
    private employeeService: EmployeeService,
    private wellnessService: WellnessService,
    private rewardsService: RewardsService,
    private goalsService: GoalsService
  ) {}

  ngOnInit(): void {
   const employeeId = localStorage.getItem('employeeId');

    if (employeeId) { this.employeeService.getEmployeeById(employeeId).subscribe({
        next: (employeeData) => {
          this.user = { ...employeeData, role: localStorage.getItem('userRole') || 'EMPLOYEE' };
          this.setGreeting();

          this.fetchAchievements();

          this.wellnessService.getWellnessMetrics(employeeId).subscribe({
            next: (metrics) => {
              // Sort by date, most recent first
              this.allMetrics = metrics.sort((a, b) => new Date(b.recordDate).getTime() - new Date(a.recordDate).getTime());
              this.updateDisplayedMetrics();
              this.prepareWeeklyChartData();
            },
            error: (err) => {
              console.error('Failed to fetch wellness metrics', err);
              this.updateDisplayedMetrics(); // Initialize with empty metrics on error
              this.prepareWeeklyChartData();
            }
          });

          this.calculateTotalPoints();
        },
        error: (err) => {
          console.error('Failed to fetch employee data', err);
         // TODO: Handle error, e.g., navigate back to login
        }
      });
    } else {
      // No employeeId found, maybe redirect to login
      console.error('No employeeId found in local storage.');
    }
 }
   calculateTotalPoints(): void {
    const employeeId = localStorage.getItem('employeeId');
    if (!employeeId) return;

    this.goalsService.getGoals(Number(employeeId)).subscribe({
      next: (goals) => {
        // Sum the 'points' from each goal. The 'points' field can be undefined, so we default to 0.
        this.totalPoints = goals.reduce((total, goal) => total + (goal.points || 0), 0);
      },
      error: (err) => {
       console.error('Failed to fetch goals for points calculation', err);
      }
    });
  }
  
  totalPoints:number=0;

  private fetchAchievements(checkForNew: boolean = false): void {
    const employeeId = localStorage.getItem('employeeId');
    if (!employeeId) return;

    this.rewardsService.getRewards(employeeId).subscribe({
        next: (rewards) => {
            const currentAchievementCount = this.recentAchievements.length;
            
          // Sort by date, most recent first
            const sortedRewards = rewards.sort((a, b) => new Date(b.achievedDate).getTime() - new Date(a.achievedDate).getTime());

            this.recentAchievements = sortedRewards.map(reward => ({
                title: reward.title,
                description: reward.description,
                icon: this.getIconForAchievement(reward.title),
                date: this.formatDateAsRelative(reward.achievedDate)
            }));

            if (checkForNew && this.recentAchievements.length > currentAchievementCount) {
                this.showAchievementToast();
            }
        },
        error: (err) => console.error('Failed to fetch achievements', err)
    });
  }

  private showAchievementToast(): void {
    this.toastMessage = 'Congrats! You just earned an achievement!';
    this.showToast = true;
    setTimeout(() => {
      this.showToast = false;
    }, 5000); // Hide after 5 seconds, matching the animation
  }

  private getIconForAchievement(title: string): string {
    const lowerCaseTitle = title.toLowerCase();
    if (lowerCaseTitle.includes('step')) {
      return 'Activity';
    }
    if (lowerCaseTitle.includes('hydration')) {
      return 'Droplets';
    }
    if (lowerCaseTitle.includes('sleep')) {
      return 'Moon';
    }
    return 'Award'; // Default icon
  }

  private formatDateAsRelative(dateString: string): string {
    const date = new Date(dateString);
    const now = new Date();
    // Adjust for timezone offset if the date string doesn't have timezone info
    const dateWithoutTime = new Date(date.valueOf() + date.getTimezoneOffset() * 60 * 1000);

    const diffTime = now.getTime() - dateWithoutTime.getTime();
    const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));

    if (diffDays < 0) return 'In the future';
    if (diffDays === 0) return 'Today';
    if (diffDays === 1) return 'Yesterday';
    if (diffDays < 7) return `${diffDays} days ago`;
    
    const diffWeeks = Math.floor(diffDays / 7);
    if (diffWeeks < 5) return `${diffWeeks} week${diffWeeks > 1 ? 's' : ''} ago`;

    return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
  }

  private setGreeting(): void {
    const currentHour = this.selectedDate.getHours();
    const timeOfDay = currentHour < 12 ? 'morning' : currentHour < 18 ? 'afternoon' : 'evening';
    const name = this.user ? `, ${this.user.name}` : '';
    this.greeting = `Good ${timeOfDay}${name}`;
  }

  canAfford(reward: DisplayReward): boolean {
      return this.userPoints.current >= reward.cost;
  }

  getPolylinePoints(metric: 'steps' | 'water' | 'sleep'): string {
    return this.weeklyChartData
      .map((day, i) => {
        const x = 65 + i * 65;
        const y = 210 - day[metric] * 2;
        return `${x},${y}`;
      })
      .join(' ');
  }

  private toLocalDateString(date: Date): string {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  private prepareWeeklyChartData(): void {
    const today = new Date();
    const last7Days: WeeklyChartData[] = [];

    for (let i = 6; i >= 0; i--) {
      const date = new Date(today);
      date.setHours(0, 0, 0, 0);
      date.setDate(today.getDate() - i);
      const dateString = this.toLocalDateString(date);
      const metricForDate = this.allMetrics.find(m => m.recordDate === dateString);

      if (metricForDate) {
        last7Days.push({
          dateLabel: date.toLocaleDateString('en-US', { weekday: 'short' }),
          steps: Math.min(100, (metricForDate.dailySteps / 10000) * 100),
          water: Math.min(100, (metricForDate.waterIntake / 8) * 100),
          sleep: Math.min(100, (metricForDate.sleepHours / 8) * 100),
        });
      } else {
        last7Days.push({
          dateLabel: date.toLocaleDateString('en-US', { weekday: 'short' }),
          steps: 0,
          water: 0,
          sleep: 0,
        });
      }
    }
    this.weeklyChartData = last7Days;
  }

  private updateDisplayedMetrics(): void {
    const selectedDateString = this.toLocalDateString(this.selectedDate);
    const metricForDate = this.allMetrics.find(m => m.recordDate === selectedDateString);

    if (metricForDate) {
      const moodScore = this.getMoodScore(metricForDate.mood);
      this.todaysMetrics = [
        { label: 'Steps', value: metricForDate.dailySteps, target: 10000, unit: 'steps', icon: '👟', progress: Math.min(100, (metricForDate.dailySteps / 10000) * 100) },
        { label: 'Water', value: metricForDate.waterIntake, target: 8, unit: 'glasses', icon: '🥛', progress: Math.min(100, (metricForDate.waterIntake / 8) * 100) },
        { label: 'Sleep', value: metricForDate.sleepHours, target: 8, unit: 'hours', icon: '🛌', progress: Math.min(100, (metricForDate.sleepHours / 8) * 100) },
        { label: 'Mood', value: metricForDate.mood || 'N/A', target: '', unit: '', icon: '😄', progress: Math.min(100, (moodScore / 5) * 100) },
      ];
    } else {
      // No data for this date, show empty state
      this.todaysMetrics = [
        { label: 'Steps', value: 0, target: 10000, unit: 'steps', icon: '👟', progress: 0 },
        { label: 'Water', value: 0, target: 8, unit: 'glasses', icon: '🥛', progress: 0 },
        { label: 'Sleep', value: 0, target: 8, unit: 'hours', icon: '🛌', progress: 0 },
        { label: 'Mood', value: 'N/A', target: '', unit: '', icon: '😄', progress: 0 },
      ];
    }
  }

  private getMoodScore(mood: string): number {
    switch (mood?.toLowerCase()) {
      case 'happy': return 5;
      case 'neutral': return 3;
      default: return 1; // For sad, stressed, etc.
    }
  }

  changeDate(offset: number): void {
    this.selectedDate.setDate(this.selectedDate.getDate() + offset);
    this.selectedDate = new Date(this.selectedDate);
    this.updateDisplayedMetrics();
  }

  isToday(): boolean {
    const today = new Date();
    return this.selectedDate.getFullYear() === today.getFullYear() &&
           this.selectedDate.getMonth() === today.getMonth() &&
           this.selectedDate.getDate() === today.getDate();
  }

  openEditModal(): void {
    const todayString = this.toLocalDateString(new Date());
    const todaysMetric = this.allMetrics.find(m => m.recordDate === todayString);

    if (todaysMetric) {
      this.editMetricData = {
        mood: todaysMetric.mood,
        sleepHours: todaysMetric.sleepHours,
        dailySteps: todaysMetric.dailySteps,
        waterIntake: todaysMetric.waterIntake
      };
    } else {
      // Reset to default if no data for today
      this.editMetricData = {
        mood: 'Neutral',
        sleepHours: 0,
        dailySteps: 0,
        waterIntake: 0
      };
    }
    this.isEditing = true;
  }

  closeEditModal(): void {
    this.isEditing = false;
  }

 saveMetrics(): void {
    if (!this.user?.employeeId) {
      console.error("Cannot save metrics, user ID is missing.");
      return;
    }

    const payload: Partial<WellnessMetric> = {
      employeeId: this.user.employeeId,
      ...this.editMetricData
    };

    this.wellnessService.submitMetrics(payload).subscribe({
      next: (savedMetric) => {
        // Update local data to reflect changes immediately
        const index = this.allMetrics.findIndex(m => m.recordDate === savedMetric.recordDate);
        if (index > -1) {
          this.allMetrics[index] = savedMetric;
        } else {
          this.allMetrics.unshift(savedMetric); // Add to the start of the array
        }
        this.allMetrics.sort((a, b) => new Date(b.recordDate).getTime() - new Date(a.recordDate).getTime());
        this.updateDisplayedMetrics();
        this.fetchAchievements(true); // Refresh achievements and check for new ones
        this.closeEditModal();
      },
      error: (err) => console.error("Failed to save metrics", err)
    });
  }
}
