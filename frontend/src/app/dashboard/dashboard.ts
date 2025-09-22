import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

// Mock data and types based on the provided dashboard.tsx
// In a real app, these would be in separate models/services.

interface Metric {
  label: string;
  value: number;
  target: number;
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
  imports: [CommonModule]
})
export class DashboardComponent implements OnInit {
  // Mock user from useAuth()
  user = {
    name: 'Emily',
    role: 'user' // 'admin' to see admin panel
  };

  greeting = '';
  
  todaysMetrics: Metric[] = [
    { label: 'Steps', value: 9200, target: 10000, unit: 'steps', icon: 'Activity', progress: 92 },
    { label: 'Water', value: 7, target: 8, unit: 'glasses', icon: 'Droplets', progress: 87 },
    { label: 'Sleep', value: 7.5, target: 8, unit: 'hours', icon: 'Moon', progress: 94 },
    { label: 'Mood', value: 4, target: 5, unit: '/5', icon: 'Heart', progress: 80 },
  ];

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
  
  today = new Date();

  ngOnInit(): void {
    this.setGreeting();
    this.setupLeaderboard();
    this.calculateTierStatus();
  }

  private setGreeting(): void {
    const currentHour = new Date().getHours();
    if (currentHour < 12) {
      this.greeting = 'Good morning';
    } else if (currentHour < 18) {
      this.greeting = 'Good afternoon';
    } else {
      this.greeting = 'Good evening';
    }
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
}
