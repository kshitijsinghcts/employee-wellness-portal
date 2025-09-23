import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeService } from '../services/employee.service';
import { Employee } from '../models/employee.model';
import { WellnessService } from '../services/wellness.service';
import { WellnessMetric } from '../models/wellness-metric.model';
import { Navbar } from '../navbar/navbar';

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

interface Achievement {
  title: string;
  description: string;
  icon: string;
  date: string;
}

interface Tier {
  name: string;
  range: string;
  minPoints: number;
  maxPoints: number;
  icon: string;
  color: string;
  gradient: string;
  benefits: string[];
}

interface LeaderboardUser {
  name: string;
  points: number;
  rank: number;
  avatar: string;
  isCurrentUser?: boolean;
}

interface Reward {
    title: string;
    cost: number;
    icon: string;
    available: boolean;
}

@Component({
  standalone: true,
  selector: 'app-dashboard',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
  imports: [CommonModule, FormsModule, Navbar]
})
export class DashboardComponent implements OnInit {
  user: Employee | null = null;
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

  recentAchievements: Achievement[] = [
    { title: 'Daily Step Goal', description: 'Reached 10,000 steps', icon: 'Activity', date: '2 days ago' },
    { title: 'Hydration Hero', description: 'Drank 8+ glasses for 3 days', icon: 'Droplets', date: '1 week ago' },
    { title: 'Sleep Champion', description: '8+ hours of sleep streak', icon: 'Moon', date: '3 days ago' },
  ];

  userPoints = {
    current: 250,
    weekly: 340,
    rank: 5,
    nextLevel: 3000,
    recentEarnings: [
      { activity: 'Completed Daily Steps', points: 50, time: '2 hours ago' },
      { activity: 'Hydration Goal Met', points: 30, time: '1 day ago' },
      { activity: 'Sleep Target Achieved', points: 40, time: '1 day ago' },
    ]
  };

  tierSystem = {
    tiers: [
      { name: 'Silver', range: '0 - 999', minPoints: 0, maxPoints: 999, icon: 'Medal', color: 'silver', gradient: 'from-gray-100 to-gray-300', benefits: ['Basic wellness tracking', 'Weekly progress reports', 'Standard support'] },
      { name: 'Gold', range: '1000 - 4999', minPoints: 1000, maxPoints: 4999, icon: 'Crown', color: 'gold', gradient: 'from-yellow-100 to-yellow-400', benefits: ['Priority support', 'Advanced analytics', 'Exclusive challenges', 'Monthly coaching call'] },
      { name: 'Platinum', range: '5000+', minPoints: 5000, maxPoints: Infinity, icon: 'Gem', color: 'platinum', gradient: 'from-purple-100 to-purple-400', benefits: ['VIP support', 'Personal wellness coach', 'Premium rewards', 'Health concierge service'] }
    ]
  };

  pointRewards: Reward[] = [
    { title: 'Coffee Voucher', cost: 500, icon: 'Gift', available: true },
    { title: 'Gym Day Pass', cost: 1000, icon: 'Activity', available: true },
    { title: 'Wellness Book', cost: 800, icon: 'Award', available: true },
    { title: 'Massage Session', cost: 2000, icon: 'Heart', available: false },
  ];

  leaderboard: LeaderboardUser[] = [];
  
  currentTier: Tier | undefined;
  currentTierIndex = 0;
  nextTier: { name: string; pointsNeeded: number; } | null = null;

  allMetrics: WellnessMetric[] = [];
  selectedDate = new Date();
  greeting = '';

  constructor(
    private employeeService: EmployeeService,
    private wellnessService: WellnessService
  ) {}

  ngOnInit(): void {
    const employeeId = localStorage.getItem('employeeId');
    if (employeeId) {
      this.employeeService.getEmployeeById(employeeId).subscribe({
        next: (employeeData) => {
          this.user = employeeData;
          this.setGreeting();
          this.setupLeaderboard();
          this.calculateTierStatus();

          this.wellnessService.getWellnessMetrics(employeeId).subscribe({
            next: (metrics) => {
              // Sort by date, most recent first
              this.allMetrics = metrics.sort((a, b) => new Date(b.recordDate).getTime() - new Date(a.recordDate).getTime());
              this.updateDisplayedMetrics();
            },
            error: (err) => {
              console.error('Failed to fetch wellness metrics', err);
              this.updateDisplayedMetrics(); // Initialize with empty metrics on error
            }
          });
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

  private setGreeting(): void {
    const currentHour = this.selectedDate.getHours();
    const timeOfDay = currentHour < 12 ? 'morning' : currentHour < 18 ? 'afternoon' : 'evening';
    const name = this.user ? `, ${this.user.name}` : '';
    this.greeting = `Good ${timeOfDay}${name}`;
  }

  private setupLeaderboard(): void {
    this.leaderboard = [
      { name: 'Sarah Johnson', points: 3240, rank: 1, avatar: 'SJ' },
      { name: 'Mike Chen', points: 2890, rank: 2, avatar: 'MC' },
      { name: 'Lisa Rodriguez', points: 2120, rank: 4, avatar: 'LR' },
      { name: 'David Kim', points: 1980, rank: 5, avatar: 'DK' },
      { name: this.user?.name || 'You', points: 250, rank: 6, avatar: this.user?.name?.charAt(0) || 'Y', isCurrentUser: true },
    ];
  }

  private calculateTierStatus(): void {
    const points = this.userPoints.current;
    if (points >= 5000) {
      this.currentTierIndex = 2; // Platinum
      this.nextTier = null;
    } else if (points >= 1000) {
      this.currentTierIndex = 1; // Gold
      this.nextTier = { name: 'Platinum', pointsNeeded: 5000 - points };
    } else {
      this.currentTierIndex = 0; // Silver
      this.nextTier = { name: 'Gold', pointsNeeded: 1000 - points };
    }
    this.currentTier = this.tierSystem.tiers[this.currentTierIndex];
  }

  getTierProgress(tierName: string): number {
    if (tierName === 'Gold') {
      return (this.userPoints.current / 1000) * 100;
    }
    if (tierName === 'Platinum') {
      return ((this.userPoints.current - 1000) / 4000) * 100;
    }
    return 0;
  }

  isTierUnlocked(tier: Tier): boolean {
    return this.userPoints.current >= tier.minPoints;
  }

  canAfford(reward: Reward): boolean {
      return this.userPoints.current >= reward.cost;
  }

  private updateDisplayedMetrics(): void {
    const selectedDateString = this.selectedDate.toISOString().split('T')[0];
    const metricForDate = this.allMetrics.find(m => m.recordDate === selectedDateString);

    if (metricForDate) {
      const moodScore = this.getMoodScore(metricForDate.mood);
      this.todaysMetrics = [
        { label: 'Steps', value: metricForDate.dailySteps, target: 10000, unit: 'steps', icon: 'Activity', progress: Math.min(100, (metricForDate.dailySteps / 10000) * 100) },
        { label: 'Water', value: metricForDate.waterIntake, target: 8, unit: 'glasses', icon: 'Droplets', progress: Math.min(100, (metricForDate.waterIntake / 8) * 100) },
        { label: 'Sleep', value: metricForDate.sleepHours, target: 8, unit: 'hours', icon: 'Moon', progress: Math.min(100, (metricForDate.sleepHours / 8) * 100) },
        { label: 'Mood', value: metricForDate.mood || 'N/A', target: '', unit: '', icon: 'Heart', progress: Math.min(100, (moodScore / 5) * 100) },
      ];
    } else {
      // No data for this date, show empty state
      this.todaysMetrics = [
        { label: 'Steps', value: 0, target: 10000, unit: 'steps', icon: 'Activity', progress: 0 },
        { label: 'Water', value: 0, target: 8, unit: 'glasses', icon: 'Droplets', progress: 0 },
        { label: 'Sleep', value: 0, target: 8, unit: 'hours', icon: 'Moon', progress: 0 },
        { label: 'Mood', value: 'N/A', target: '', unit: '', icon: 'Heart', progress: 0 },
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
    const todayString = new Date().toISOString().split('T')[0];
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
        this.closeEditModal();
      },
      error: (err) => console.error("Failed to save metrics", err)
    });
  }
}
